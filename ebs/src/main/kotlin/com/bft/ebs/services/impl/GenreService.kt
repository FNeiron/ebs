package com.bft.ebs.services.impl

import com.bft.ebs.dto.GenreDto
import com.bft.ebs.repositories.impl.GenreRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.GenreService
import org.springframework.stereotype.Service

@Service
class GenreService(private val genreRepository: GenreRepository) :
    GenreService {
    override fun getGenre(id: Long): GenreDto {
        return EbsMapper.mapGenreToGenreDto(genreRepository.read(id))
    }

    override fun getAllGenres(): List<GenreDto> {
        return genreRepository.findAll().map { EbsMapper.mapGenreToGenreDto(it) }
    }

    override fun createGenre(genreDto: GenreDto): Long {
        return genreRepository.create(EbsMapper.mapGenreDtoToGenre(genreDto)).id ?: throw IllegalStateException("Genre already exists")
    }

    override fun deleteGenre(id: Long): Boolean {
        return genreRepository.delete(id)
    }

    override fun updateGenre(genreDto: GenreDto): Boolean {
        return genreRepository.update(EbsMapper.mapGenreDtoToGenre(genreDto))
    }
}