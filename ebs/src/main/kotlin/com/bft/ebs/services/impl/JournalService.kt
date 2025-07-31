package com.bft.ebs.services.impl

import com.bft.ebs.dto.JournalDto
import com.bft.ebs.repositories.impl.JournalRepository
import com.bft.ebs.services.EbsMapper
import com.bft.ebs.services.interfaces.JournalService
import org.springframework.stereotype.Service
import java.sql.Date

@Service
class JournalService(private val journalRepository: JournalRepository) :
    JournalService {
    override fun getJournalRecord(id: Long): JournalDto {
        return EbsMapper.mapJournalToJournalDto(journalRepository.read(id))
    }

    override fun getAllJournalRecords(): List<JournalDto> {
        return journalRepository.findAll().map { EbsMapper.mapJournalToJournalDto(it) }
    }

    override fun getAllOpenedJournalRecords(): List<JournalDto> {
        return journalRepository.findAllOpened().map { EbsMapper.mapJournalToJournalDto(it) }
    }

    override fun createJournalRecord(journalDto: JournalDto): Long {
        return journalRepository.create( EbsMapper.mapJournalDtoToJournal(journalDto)).id
            ?: throw IllegalStateException("Creation error")
    }

    override fun deleteJournalRecord(id: Long): Boolean {
        return journalRepository.delete(id)
    }

    override fun updateJournalRecord(journalDto: JournalDto): Boolean {
        return journalRepository.update(EbsMapper.mapJournalDtoToJournal(journalDto))
    }

    override fun returnBookByIsbnAndReaderId(bookIsbn: String, readerId: Long, endDate: Date): Boolean {
        val record = journalRepository.findDebtorsByBookIsbnAndReaderId(bookIsbn, readerId)

        if (record.isEmpty() || record[0].startDate > endDate)
            return false

        return journalRepository.update(record.minBy { it.startDate.time }.copy(endDate = endDate))
    }

    override fun returnBookByJournalId(journalId: Long, endDate: Date): Boolean {
        val record = journalRepository.read(journalId)

        return journalRepository.update(record.copy(endDate = endDate))
    }

    override fun debtBookByBookIdAndReaderId(bookInLibId: Long, readerId: Long): JournalDto {
        val journalDto = EbsMapper.mapBookInLibIdAndReaderIdToJournalDto(bookInLibId, readerId)
        return EbsMapper.mapJournalToJournalDto(journalRepository.create(journalDto))
    }
}