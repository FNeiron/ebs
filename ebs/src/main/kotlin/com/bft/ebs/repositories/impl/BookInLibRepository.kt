package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Book
import com.bft.ebs.models.BookInLib
import com.bft.ebs.repositories.interfaces.BookInLibRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class BookInLibRepository(private val jdbcTemplate: JdbcTemplate,
                          private val bookRepository: BookRepository) :
    BookInLibRepository {

    override fun create(entity: BookInLib): BookInLib {
        if (entity.book.id == null) throw IllegalArgumentException("book id cannot be null")

        val sql = "INSERT INTO books_in_lib (book_id) VALUES (?) RETURNING id"

        val generatedId: Long = jdbcTemplate.queryForObject(sql,
            entity.book.id) { rs, _ ->
            rs.getLong("id")
        }

        return entity.copy(id = generatedId)
    }

    @Transactional
    override fun read(id: Long): BookInLib {
        val sql = "SELECT * from books_in_lib WHERE id =?"

        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            BookInLib(
                id = rs.getLong("id"),
                book = bookRepository.read(rs.getLong("book_id"))
            )
        }
    }

    override fun update(entity: BookInLib): Boolean {
        val sql = "UPDATE books_in_lib SET book_id = ? WHERE id = ?"
        return jdbcTemplate.update(sql, entity.book.id, entity.id) > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM books_in_lib WHERE id=?"
        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<BookInLib> {
        val sql = """
            SELECT books_in_lib.id as "books_in_lib.id", books.id as "books.id", books.name as "books.name",
            books.isbn as "books.isbn", books.publication_date as "books.publication_date"
            FROM books_in_lib
            JOIN books ON books_in_lib.book_id = books.id
            """
        return jdbcTemplate.query(sql) { rs, _ ->
            BookInLib(
                id = rs.getLong("books_in_lib.id"),
                book = Book(
                    id = rs.getLong("books.id"),
                    stories = null,
                    name = rs.getString("books.name"),
                    isbn = rs.getString("books.isbn"),
                    publicationDate = rs.getDate("books.publication_date")
                )
            )
        }
    }
}