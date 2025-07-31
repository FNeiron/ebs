package com.bft.ebs.repositories.interfaces

import com.bft.ebs.models.Journal

interface JournalRepository : Repository<Journal, Long> {
    fun findAllOpened(): List<Journal>
    fun findAllDeadlineDebtors(): List<Journal>
    fun findDebtorsByBookIsbnAndReaderId(bookIsbn: String, readerId: Long): List<Journal>
    fun findDedlineDebtorsByBookIsbnAndReaderId(bookIsbn: String, readerId: Long): List<Journal>
}