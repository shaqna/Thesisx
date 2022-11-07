package com.ngedev.thesisx.domain.model

data class User(
    val uid: String,
    val username: String,
    val email: String,
    val favorite: List<String> = listOf(),
    val borrowing: List<String>? = listOf()
)
