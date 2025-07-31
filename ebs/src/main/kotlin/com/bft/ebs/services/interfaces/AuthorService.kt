package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.AuthorDto

interface AuthorService {
    fun getAuthor(id: Long): AuthorDto
    fun getAllAuthors(): List<AuthorDto>
    fun createAuthor(authorDto: AuthorDto): Long
    fun deleteAuthor(id: Long): Boolean
    fun updateAuthor(authorDto: AuthorDto): Boolean
}