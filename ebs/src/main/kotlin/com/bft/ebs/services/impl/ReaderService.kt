package com.bft.ebs.services.impl

import com.bft.ebs.dto.ReaderDto
import com.bft.ebs.repositories.impl.ReaderRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.ReaderService
import org.springframework.stereotype.Service

@Service
class ReaderService(private val readerRepository: ReaderRepository) :
    ReaderService {
    override fun getReader(id: Long): ReaderDto {
        return EbsMapper.mapReaderToReaderDto(readerRepository.read(id))
    }

    override fun getAllReaders(): List<ReaderDto> {
        return readerRepository.findAll().map { EbsMapper.mapReaderToReaderDto(it) }
    }

    override fun createReader(readerDto: ReaderDto): Long {
        return readerRepository.create(
            EbsMapper.mapReaderDtoToReader(readerDto)
        ).id ?: throw IllegalStateException("Couldn't create the reader'")
    }

    override fun deleteReader(id: Long): Boolean {
        return readerRepository.delete(id)
    }

    override fun updateReader(readerDto: ReaderDto): Boolean {
        return readerRepository.update(EbsMapper.mapReaderDtoToReader(readerDto))
    }
}