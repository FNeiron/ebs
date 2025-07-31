package com.bft.ebs.dto

import java.sql.Date

data class JournalDto (
    val id: Long?,
    val book: BookInLibDto,
    val reader: ReaderDto,
    val startDate: Date?,
    val endDate: Date?
)