package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.BookDto

interface BookService {
    fun getBook(id: Long): BookDto
    fun getAllBooks(): List<BookDto>
    fun createBook(bookDto: BookDto): Long
    fun deleteBook(id: Long): Boolean
    fun updateBook(bookDto: BookDto): Boolean
}