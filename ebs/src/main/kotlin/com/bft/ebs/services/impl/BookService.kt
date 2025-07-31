package com.bft.ebs.services.impl

import com.bft.ebs.dto.BookDto
import com.bft.ebs.repositories.impl.BookRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.BookService
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) :
    BookService {
    override fun getBook(id: Long): BookDto {
        return EbsMapper.mapBookToBookDto(bookRepository.read(id))
    }

    override fun getAllBooks(): List<BookDto> {
        return bookRepository.findAll().map { EbsMapper.mapBookToBookDto(it) }
    }

    override fun createBook(bookDto: BookDto): Long {
        return bookRepository.create(EbsMapper.mapBookDtoToBook(bookDto)).id
            ?: throw IllegalStateException("Book already exists")
    }

    override fun deleteBook(id: Long): Boolean {
        return bookRepository.delete(id)
    }

    override fun updateBook(bookDto: BookDto): Boolean {
        return bookRepository.update(EbsMapper.mapBookDtoToBook(bookDto))
    }
}