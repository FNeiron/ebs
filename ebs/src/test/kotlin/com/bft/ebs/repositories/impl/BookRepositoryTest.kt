package com.bft.ebs.repositories.impl

import com.bft.ebs.models.Author
import com.bft.ebs.models.Book
import com.bft.ebs.models.Genre
import com.bft.ebs.models.Story
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.sql.Date

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    lateinit var bookRepository: BookRepository

    @Test
    fun create() {
        val book = Book(
            id = null,
            name = "Test Book",
            stories = listOf(Story(
                id = 2,
                authors = listOf(Author(
                    id = 3,
                    name = "Tatianova Tatiana Tatovna",
                    nickname = "Miranda",
                    birthday = Date.valueOf("2001-12-12")),
                    Author(
                        id = 1,
                        name = "Fazylov Ruslan Tahirovich",
                        nickname = "Faz",
                        birthday = Date.valueOf("2002-09-02")
                )),
                genre = Genre(
                    id = 14,
                    name = "science"
                ),
                name = "Computer Science"
            )),
            isbn = "978-3-16-148410-0",
            publicationDate = Date.valueOf("2022-01-01"))
        val createdBook = bookRepository.create(book)
        assertEquals(4, createdBook.id)
    }

    @Test
    fun read() {
        val book = bookRepository.read(1)
        assertNotNull(book)
        assertEquals("Panoma", book.name)
        assertEquals(1, book.stories?.size)
    }

    @Test
    fun update() {
        val book = bookRepository.read(2).copy(name = "Updated Test Book")
        assertTrue(bookRepository.update(book))

        val updatedBook = bookRepository.read(2)
        assertEquals(updatedBook.name, "Updated Test Book")

        val updatedStories = listOf(updatedBook.stories?.get(0)?.copy(name = "Updated Computer Science")?:
        throw IllegalStateException("Неизвестная ошибка, которая привела к тому, что в книге нет историй."))
        assertTrue(bookRepository.update(book.copy(stories = updatedStories)))

        val updatedBook1 = bookRepository.read(2)
        assertEquals("Updated Computer Science", updatedBook1.stories?.get(0)?.name)
    }

    @Test
    fun deleteIfExists() {
        assertTrue(bookRepository.delete(3))
    }

    @Test
    fun deleteIfNotExists() {
        assertFalse(bookRepository.delete(100))
    }

    @Test
    fun findAll() {
        val books = bookRepository.findAll()
        assertNotNull(books)
        assertTrue(books.isNotEmpty())
    }
}