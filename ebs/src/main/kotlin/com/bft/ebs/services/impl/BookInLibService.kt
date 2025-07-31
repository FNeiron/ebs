package com.bft.ebs.services.impl

import com.bft.ebs.dto.BookInLibDto
import com.bft.ebs.repositories.impl.BookInLibRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.BookInLibService
import org.springframework.stereotype.Service

@Service
class BookInLibService(private val bookInLibRepository: BookInLibRepository) :
    BookInLibService {
    override fun getBookInLibrary(id: Long): BookInLibDto {
        return EbsMapper.mapBookInLibToBookInLibDto(bookInLibRepository.read(id))
    }

    override fun getAllBooksInLibrary(): List<BookInLibDto> {
        return bookInLibRepository.findAll().map { EbsMapper.mapBookInLibToBookInLibDto(it) }
    }

    override fun createBookInLibrary(bookInLibDto: BookInLibDto): Long {
        return bookInLibRepository.create(EbsMapper.mapBookInLibDtoToBookInLib(bookInLibDto)).id ?:
        throw IllegalStateException("Book in library already")
    }

    override fun deleteBookInLibrary(id: Long): Boolean {
        return bookInLibRepository.delete(id)
    }

    override fun updateBookInLibrary(bookInLibDto: BookInLibDto): Boolean {
        return bookInLibRepository.update(EbsMapper.mapBookInLibDtoToBookInLib(bookInLibDto))
    }
}