package com.bft.ebs.repositories.impl

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JournalRepositoryTest {
    @Autowired
    lateinit var journalRepository: JournalRepository
    @Value("\${period}")
    private val period: Long = 0
    @Test
    fun findAllDebtors() {
        val debtors = journalRepository.findAllDeadlineDebtors()
        assertTrue(debtors.isNotEmpty())
        debtors.forEach {
            assertTrue(it.endDate == null)
            assertTrue(it.startDate < java.sql.Date.valueOf(java.time.LocalDate.now().minusDays(period)))
        }
    }
}