package com.bft.ebs.dto

import java.sql.Date

data class BookDto (
    val id: Long?,
    val stories: List<StoryDto>?,
    val name: String?,
    val isbn: String?,
    val publicationDate: Date?
)