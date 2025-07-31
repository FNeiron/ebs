package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Book
import com.bft.ebs.models.BookInLib
import com.bft.ebs.models.Journal
import com.bft.ebs.models.Reader
import com.bft.ebs.repositories.interfaces.JournalRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.queryForObject
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class JournalRepository(private val jdbcTemplate: JdbcTemplate,
                        private val bookInLibRepository: BookInLibRepository) :
    JournalRepository {

    @Value("\${period}")
    private val period: String? = null

    private val sqlForFindAll = """
            SELECT journal.id as "journal.id", journal.book_in_lib_id as "journal.book_in_lib_id",
            books.id as "books.id", books.name as "books.name", books.isbn as "books.isbn",
            books.publication_date as "books.publication_date", journal.reader_id as "journal.reader_id",
            readers.name as "readers.name", readers.email as "readers.email", journal.start_date as "journal.start_date",
            journal.end_date as "journal.end_date"
            FROM journal
            JOIN books_in_lib ON books_in_lib.id = journal.book_in_lib_id
            JOIN books ON books_in_lib.book_id = books.id
            JOIN readers ON journal.reader_id = readers.id
        """

    override fun create(entity: Journal): Journal {
        if (entity.reader.id == null || entity.book.id == null)
            throw IllegalArgumentException("reader id and book_id cannot be null")

        val sql = "INSERT INTO journal (book_in_lib_id, reader_id, start_date, end_date) VALUES (?, ?, ?, NULL) RETURNING id"

        val generatedId: Long = jdbcTemplate.queryForObject(sql,
            entity.book.id,
            entity.reader.id,
            entity.startDate) { rs, _ ->
            rs.getLong("id")
        }
        return entity.copy(id = generatedId)
    }

    @Transactional
    override fun read(id: Long): Journal {
        val sql = """
            SELECT journal.id as "journal.id", books_in_lib.id as "books_in_lib.id", readers.id as "readers.id",
            readers.name as "readers.name", readers.email as "readers.email", journal.start_date as "journal.start_date",
            journal.end_date as "journal.end_date"
            FROM journal
            LEFT JOIN books_in_lib ON journal.book_in_lib_id = books_in_lib.id
            LEFT JOIN readers ON journal.reader_id = readers.id
            WHERE journal.id =?
        """

        return jdbcTemplate.queryForObject(sql, id) { rs, _ ->
            Journal(
                id = rs.getLong("journal.id"),
                book = bookInLibRepository.read(rs.getLong("books_in_lib.id")),
                reader = Reader(
                    id = rs.getLong("readers.id"),
                    name = rs.getString("readers.name"),
                    email = rs.getString("readers.email")
                ),
                startDate = rs.getDate("journal.start_date"),
                endDate = rs.getDate("journal.end_date")
            )
        }
    }

    override fun update(entity: Journal): Boolean {
        val sql = "UPDATE journal SET end_date=? WHERE id=?"

        return jdbcTemplate.update(sql, entity.endDate, entity.id) > 0
    }

    override fun delete(id: Long): Boolean {
        val sql = "DELETE FROM journal WHERE id=?"
        return jdbcTemplate.update(sql, id) > 0
    }

    override fun findAll(): List<Journal> {
        return jdbcTemplate.query(sqlForFindAll) { rs, _ ->
            Journal(
                id = rs.getLong("journal.id"),
                book = BookInLib(
                    id = rs.getLong("journal.book_in_lib_id"),
                    book = Book(
                        id = rs.getLong("books.id"),
                        stories = null,
                        name = rs.getString("books.name"),
                        isbn = rs.getString("books.isbn"),
                        publicationDate = rs.getDate("books.publication_date")
                )),
                reader = Reader(
                    id = rs.getLong("journal.reader_id"),
                    name = rs.getString("readers.name"),
                    email = rs.getString("readers.email")
                ),
                startDate = rs.getDate("journal.start_date"),
                endDate = rs.getDate("journal.end_date")
            )
        }
    }

    override fun findAllOpened(): List<Journal> {
        val sql = sqlForFindAll +
            "WHERE journal.end_date IS NULL"
        return jdbcTemplate.query(sql) { rs, _ ->
            Journal(
                id = rs.getLong("journal.id"),
                book = BookInLib(
                    id = rs.getLong("journal.book_in_lib_id"),
                    book = Book(
                        id = rs.getLong("books.id"),
                        stories = null,
                        name = rs.getString("books.name"),
                        isbn = rs.getString("books.isbn"),
                        publicationDate = rs.getDate("books.publication_date")
                    )),
                reader = Reader(
                    id = rs.getLong("journal.reader_id"),
                    name = rs.getString("readers.name"),
                    email = rs.getString("readers.email")
                ),
                startDate = rs.getDate("journal.start_date"),
                endDate = rs.getDate("journal.end_date")
            )
        }
    }

    override fun findAllDeadlineDebtors(): List<Journal> {
        val sql = sqlForFindAll +
            "WHERE journal.end_date IS NULL and journal.start_date < CURRENT_DATE - $period"

        return jdbcTemplate.query(sql) { rs, _ ->
            Journal(
                id = rs.getLong("journal.id"),
                book = BookInLib(
                    id = rs.getLong("journal.book_in_lib_id"),
                    book = Book(
                        id = rs.getLong("books.id"),
                        stories = null,
                        name = rs.getString("books.name"),
                        isbn = rs.getString("books.isbn"),
                        publicationDate = rs.getDate("books.publication_date")
                    )),
                reader = Reader(
                    id = rs.getLong("journal.reader_id"),
                    name = rs.getString("readers.name"),
                    email = rs.getString("readers.email")
                ),
                startDate = rs.getDate("journal.start_date"),
                endDate = rs.getDate("journal.end_date")
            )
        }
    }

    override fun findDedlineDebtorsByBookIsbnAndReaderId(bookIsbn: String, readerId: Long): List<Journal> {
        val sql = sqlForFindAll +
                """WHERE journal.end_date IS NULL AND journal.start_date < CURRENT_DATE - $period
                    AND readers.id = $readerId AND books.isbn = '$bookIsbn'
                """.trimMargin()

        return jdbcTemplate.query(sql) { rs, _ ->
            Journal(
                id = rs.getLong("journal.id"),
                book = BookInLib(
                    id = rs.getLong("journal.book_in_lib_id"),
                    book = Book(
                        id = rs.getLong("books.id"),
                        stories = null,
                        name = rs.getString("books.name"),
                        isbn = rs.getString("books.isbn"),
                        publicationDate = rs.getDate("books.publication_date")
                    )),
                reader = Reader(
                    id = rs.getLong("journal.reader_id"),
                    name = rs.getString("readers.name"),
                    email = rs.getString("readers.email")
                ),
                startDate = rs.getDate("journal.start_date"),
                endDate = rs.getDate("journal.end_date")
            )
        }
    }

    override fun findDebtorsByBookIsbnAndReaderId(bookIsbn: String, readerId: Long): List<Journal> {
        val sql = sqlForFindAll +
                """WHERE journal.end_date IS NULL
                    AND readers.id = $readerId AND books.isbn = '$bookIsbn'
                """.trimMargin()

        return jdbcTemplate.query(sql) { rs, _ ->
            Journal(
                id = rs.getLong("journal.id"),
                book = BookInLib(
                    id = rs.getLong("journal.book_in_lib_id"),
                    book = Book(
                        id = rs.getLong("books.id"),
                        stories = null,
                        name = rs.getString("books.name"),
                        isbn = rs.getString("books.isbn"),
                        publicationDate = rs.getDate("books.publication_date")
                    )),
                reader = Reader(
                    id = rs.getLong("journal.reader_id"),
                    name = rs.getString("readers.name"),
                    email = rs.getString("readers.email")
                ),
                startDate = rs.getDate("journal.start_date"),
                endDate = rs.getDate("journal.end_date")
            )
        }
    }
}