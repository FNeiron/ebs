package com.bft.ebs.services.impl

import com.bft.ebs.dto.AuthorDto
import com.bft.ebs.repositories.impl.AuthorRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.AuthorService
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepository: AuthorRepository) :
    AuthorService {
    override fun getAuthor(id: Long): AuthorDto {
        return EbsMapper.mapAuthorToAuthorDto(authorRepository.read(id))
    }

    override fun getAllAuthors(): List<AuthorDto> {
        return authorRepository.findAll().map {
            EbsMapper.mapAuthorToAuthorDto(it)
        }
    }

    override fun createAuthor(authorDto: AuthorDto): Long {
        return authorRepository.create(EbsMapper.mapAuthorDtoToAuthor(authorDto)).id
            ?: throw IllegalStateException("Couldn't create author.")
    }

    override fun deleteAuthor(id: Long): Boolean {
        return authorRepository.delete(id)
    }

    override fun updateAuthor(authorDto: AuthorDto): Boolean {
        return authorRepository.update(
            EbsMapper.mapAuthorDtoToAuthor(authorDto)
        )
    }
}