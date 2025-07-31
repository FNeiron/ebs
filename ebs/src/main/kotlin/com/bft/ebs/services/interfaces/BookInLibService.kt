package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.BookInLibDto

interface BookInLibService {
    fun getBookInLibrary(id: Long): BookInLibDto
    fun getAllBooksInLibrary(): List<BookInLibDto>
    fun createBookInLibrary(bookInLibDto: BookInLibDto): Long
    fun deleteBookInLibrary(id: Long): Boolean
    fun updateBookInLibrary(bookInLibDto: BookInLibDto): Boolean
}