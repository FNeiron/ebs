package com.bft.ebs.models

import java.sql.Date

data class Journal(
    val id: Long?,
    val book: BookInLib,
    val reader: Reader,
    val startDate: Date,
    val endDate: Date?
)
