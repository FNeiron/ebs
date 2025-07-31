package com.bft.ebs.models

import java.sql.Date

data class Book(
    val id: Long?,
    val stories: List<Story>?,
    val name: String,
    val isbn: String,
    val publicationDate: Date
)
