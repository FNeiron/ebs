package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Author
import com.bft.ebs.models.Genre
import com.bft.ebs.models.Story
import com.bft.ebs.repositories.interfaces.StoryRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.query
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class StoryRepository(private val jdbcTemplate: JdbcTemplate,
                      private val authorRepository: AuthorRepository) :
    StoryRepository {

    val insertIntoAuthorsStories = "INSERT INTO authors_stories (author_id, story_id) VALUES (?, ?)"

    @Transactional
    override fun create(entity: Story): Story {
        if (entity.genre.id == null) throw IllegalStateException("genre id cannot be null.")
        if (entity.authors == null) throw IllegalStateException("list of authors cannot be empty.")

        val sql = "INSERT INTO stories (genre_id, name) VALUES (?, ?) RETURNING id"
        val generatedId: Long = jdbcTemplate.queryForObject(
            sql,
            entity.genre.id,
            entity.name
        ) { rs, _ ->
            rs.getLong("id")
        }

        entity.authors.forEach { author ->
            if (author.id == null)
                authorRepository.create(author)
        }

        val authorsIdsOfStory = entity.authors.map { arrayOf(it.id, generatedId) }

        jdbcTemplate.batchUpdate(insertIntoAuthorsStories, authorsIdsOfStory)


        return entity.copy(id = generatedId)
    }

    override fun read(id: Long): Story {
        val sql = """
        SELECT stories.id as "stories.id", stories.genre_id as "stories.genre_id", genres.genre as "genres.genre",
        stories.name as "stories.name", authors.id as "authors.id", authors.name as "authors.name",
        authors.nickname as "authors.nickname", authors.birthday as "authors.birthday"
        FROM stories 
        JOIN genres ON stories.genre_id = genres.id
        LEFT JOIN authors_stories ON public.authors_stories.story_id = stories.id
        LEFT JOIN authors ON authors_stories.author_id = authors.id
        WHERE stories.id = ?
    """

        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            val storyId = rs.getLong("stories.id")
            if (storyId == 0L) throw NoSuchElementException("story(id=$id) not found.")
            val genre = Genre(
                id = rs.getLong("stories.genre_id"),
                name = rs.getString("genres.genre")
            )
            val storyName = rs.getString("stories.name")

            // Создаем список авторов
            val authors = mutableListOf<Author>()
            do {
                val authorId = rs.getLong("authors.id")
                if (!rs.wasNull()) {
                    authors.add(
                        Author(
                            id = authorId,
                            name = rs.getString("authors.name"),
                            nickname = rs.getString("authors.nickname"),
                            birthday = rs.getDate("authors.birthday")
                        )
                    )
                }
            } while (rs.next())

            Story(
                id = storyId,
                genre = genre,
                name = storyName,
                authors = authors
            )
        }
    }

    @Transactional
    override fun update(entity: Story): Boolean {
        if (entity.id == null) throw IllegalArgumentException("story id is null.")
        if (entity.authors == null) throw IllegalArgumentException("list of authors is empty.")

        val currentAuthors = entity.authors.map { author ->
            if (author.id == null) {
                val authorId = authorRepository.create(author).id
                jdbcTemplate.update(
                    insertIntoAuthorsStories,
                    authorId,
                    entity.id
                )
                author.copy(id=authorId)
            }
            else
                author
        }

        jdbcTemplate.update(
            "DELETE FROM authors_stories WHERE story_id =?",
            entity.id
        )

        val updatedAuthors = currentAuthors.map { arrayOf(it.id, entity.id) }

        val updatedRows = jdbcTemplate.batchUpdate("INSERT INTO authors_stories (author_id, story_id) VALUES (?, ?)", updatedAuthors).size

        val sql = "UPDATE stories SET genre_id =?, name =? WHERE id =?"
        return jdbcTemplate.update(sql, entity.genre.id, entity.name, entity.id) > 0 || updatedRows > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM stories WHERE id = ?"
        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<Story> {
        val sql = """
            SELECT stories.id as "stories.id", stories.genre_id as "stories.genre_id", genres.genre as "genres.genre",
            stories.name as "stories.name"
            FROM stories
            JOIN genres ON stories.genre_id = genres.id
        """
        return jdbcTemplate.query(sql) { rs, _ ->
            Story(
                id = rs.getLong("stories.id"),
                authors = null,
                genre = Genre(
                    id = rs.getLong("stories.genre_id"),
                    name = rs.getString("genres.genre")
                ),
                name = rs.getString("stories.name")
            )
        }
    }

    override fun findAuthorsByStoryId(storyId: Long): List<Author> {
        val sql = """
            SELECT authors.id as "authors.id", authors.name as "authors.name", authors.nickname as "authors.nickname",
            authors.birthday as "authors.birthday"
            FROM authors_stories
            JOIN authors ON authors_stories.author_id = authors.id
            WHERE authors_stories.story_id =?
        """
        return jdbcTemplate.query(sql, storyId) { rs, _ ->
            Author(
                id = rs.getLong("authors.id"),
                name = rs.getString("authors.name"),
                nickname = rs.getString("authors.nickname"),
                birthday = rs.getDate("authors.birthday")
            )
        }
    }
}