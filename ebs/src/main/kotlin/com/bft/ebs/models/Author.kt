package com.bft.ebs.models

import java.sql.Date

data class Author(
    val id: Long?,
    val name: String,
    val nickname: String,
    val birthday: Date?
)