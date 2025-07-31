package com.bft.ebs.dto

data class StoryDto (
    val id: Long?,
    val authors: List<AuthorDto>?,
    val genre: GenreDto?,
    val name: String?
)