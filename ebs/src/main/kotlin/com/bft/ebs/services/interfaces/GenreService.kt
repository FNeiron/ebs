package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.GenreDto

interface GenreService {
    fun getGenre(id: Long): GenreDto
    fun getAllGenres(): List<GenreDto>
    fun createGenre(genreDto: GenreDto): Long
    fun deleteGenre(id: Long): Boolean
    fun updateGenre(genreDto: GenreDto): Boolean
}