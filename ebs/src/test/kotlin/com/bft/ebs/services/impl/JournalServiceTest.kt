package com.bft.ebs.services.impl

import com.bft.ebs.repositories.impl.JournalRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JournalServiceTest {

    @Autowired
    private lateinit var journalService: JournalService

    @Autowired
    private lateinit var journalRepository: JournalRepository

    @Test
    fun returnBooks() {
        val bookIsbn = "200-201-333"
        val readerId = 1L
        val debtors = journalRepository.findDedlineDebtorsByBookIsbnAndReaderId(bookIsbn, readerId)
        assertEquals(true, journalService.returnBookByIsbnAndReaderId(bookIsbn, readerId))

        val debMinStartDateId = debtors.minBy { it.startDate.time }.id!!
        val closedDebtor = journalRepository.read(debMinStartDateId).endDate
        assertEquals(true, closedDebtor != null)

        assertEquals(true, journalService.returnBookByIsbnAndReaderId(bookIsbn, readerId))
        assertEquals(true, journalRepository.findAllDeadlineDebtors().isEmpty())

        assertEquals(false, journalService.returnBookByIsbnAndReaderId(bookIsbn, readerId))
    }
}