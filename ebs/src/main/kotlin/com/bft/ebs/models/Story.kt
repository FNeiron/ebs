package com.bft.ebs.models

data class Story(
    val id: Long?,
    val authors: List<Author>?,
    val genre: Genre,
    val name: String
)
