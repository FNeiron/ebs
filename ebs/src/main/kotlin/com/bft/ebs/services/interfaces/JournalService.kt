package com.bft.ebs.services.interfaces

import com.bft.ebs.dto.JournalDto
import java.sql.Date

interface JournalService {
    fun getJournalRecord(id: Long): JournalDto
    fun getAllJournalRecords(): List<JournalDto>
    fun getAllOpenedJournalRecords(): List<JournalDto>
    fun createJournalRecord(journalDto: JournalDto): Long
    fun deleteJournalRecord(id: Long): Boolean
    fun updateJournalRecord(journalDto: JournalDto): Boolean
    fun returnBookByIsbnAndReaderId(bookIsbn: String, readerId: Long, endDate: Date = Date(java.util.Date().time)): Boolean
    fun returnBookByJournalId(journalId: Long, endDate: Date = Date(java.util.Date().time)): Boolean
    fun debtBookByBookIdAndReaderId(bookInLibId: Long, readerId: Long): JournalDto
}