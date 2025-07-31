package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Author
import com.bft.ebs.models.Book
import com.bft.ebs.models.Genre
import com.bft.ebs.models.Story
import com.bft.ebs.repositories.interfaces.BookRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.PreparedStatement

@Repository
class BookRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val storyRepository: StoryRepository
) : BookRepository {

    @Transactional
    override fun create(entity: Book): Book {
         if (entity.stories.isNullOrEmpty()) throw IllegalArgumentException("No stories")

        val sql = "INSERT INTO books (name, isbn, publication_date) VALUES (?, ?, ?)"

        val keyHolder = GeneratedKeyHolder()

        // До этого здесь был SELECT id FROM books ORDER BY id DESC LIMIT 1. Так как H2 не поддерживает RETURNING id.
        jdbcTemplate.update({ connection ->
            val ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
            ps.setString(1, entity.name)
            ps.setString(2, entity.isbn)
            ps.setDate(3, entity.publicationDate)
            ps
        }, keyHolder)

        val generatedId: Long = keyHolder.keys?.get("id").toString().toLong()

        entity.stories.forEach { story ->
            if (story.id == null)
                storyRepository.create(story)
        }

        val storiesIdsOfBook = entity.stories.map { arrayOf(it.id, generatedId) }

        jdbcTemplate.batchUpdate("INSERT INTO stories_books (story_id, book_id) VALUES (?, ?)", storiesIdsOfBook)

        return entity.copy(id = generatedId)
    }

    override fun read(id: Long): Book {
        val sql = """
            SELECT books.id as "books.id", books.name as "books.name", books.isbn as "books.isbn",
            books.publication_date as "books.publication_date", stories.id as "stories.id", authors.id as "authors.id",
            authors.name as "authors.name", authors.nickname as "authors.nickname", authors.birthday as "authors.birthday",
            genres.id as "genres.id", genres.genre as "genres.genre", stories.name as "stories.name"
            FROM books
            JOIN stories_books ON stories_books.id = books.id
            JOIN stories ON stories_books.story_id = stories.id
            JOIN genres ON stories.genre_id = genres.id
            JOIN authors_stories ON authors_stories.story_id = stories.id
            JOIN authors ON authors_stories.author_id = authors.id
            WHERE books.id=?
            """

        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            val bookId = rs.getLong("books.id")
            if (bookId == 0L) throw NoSuchElementException("book(id=$id) not found.")

            val bookName = rs.getString("books.name")
            val bookISBN = rs.getString("books.isbn")
            val bookPublicationDate = rs.getDate("books.publication_date")

            val authors = mutableMapOf<Long, MutableList<Author>>()
            val stories = mutableListOf<Story>()

            do {
                val storyId = rs.getLong("stories.id")
                if (!authors.containsKey(storyId)) {
                    authors[storyId] = mutableListOf()
                    authors[storyId]?.add(
                        Author(
                            id = rs.getLong("authors.id"),
                            name = rs.getString("authors.name"),
                            nickname = rs.getString("authors.nickname"),
                            birthday = rs.getDate("authors.birthday")
                        )
                    )
                }

                if (!stories.any { it.id == storyId })
                    stories.add(
                        Story(
                            id = storyId,
                            genre = Genre(
                                id = rs.getLong("genres.id"),
                                name = rs.getString("genres.genre")
                            ),
                            name = rs.getString("stories.name"),
                            authors = null
                        )
                    )

            } while (rs.next())

            val fulledStories = mutableListOf<Story>()

            stories.forEach {story ->
                fulledStories.add(story.copy(authors = authors[story.id]))
            }

            Book(
                id = bookId,
                name = bookName,
                isbn = bookISBN,
                publicationDate = bookPublicationDate,
                stories = fulledStories
            )
        }
    }

    @Transactional
    override fun update(entity: Book): Boolean {
        if (entity.stories.isNullOrEmpty()) throw IllegalArgumentException("No stories")
        for (story in entity.stories)
            storyRepository.update(story)

        return jdbcTemplate.update("UPDATE books SET name=?, isbn=?, publication_date=? WHERE id=?",
            entity.name,
            entity.isbn,
            entity.publicationDate,
            entity.id
        ) > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM books where id=?"
        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<Book> {
        val sql = "SELECT * FROM books"
        return jdbcTemplate.query(sql) { rs, _ ->
            Book(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                isbn = rs.getString("isbn"),
                publicationDate = rs.getDate("publication_date"),
                stories = null
            )
        }
    }
}