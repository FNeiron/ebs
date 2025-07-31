package com.bft.ebs.dto

import java.sql.Date

data class AuthorDto (
    val id: Long?,
    val name: String?,
    val nickname: String?,
    val birthday: Date?
)