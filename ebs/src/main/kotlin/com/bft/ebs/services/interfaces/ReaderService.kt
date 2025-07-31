package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.ReaderDto

interface ReaderService {
    fun getReader(id: Long): ReaderDto
    fun getAllReaders(): List<ReaderDto>
    fun createReader(readerDto: ReaderDto): Long
    fun deleteReader(id: Long): Boolean
    fun updateReader(readerDto: ReaderDto): Boolean
}