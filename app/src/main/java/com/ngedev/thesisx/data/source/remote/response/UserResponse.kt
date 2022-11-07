package com.ngedev.thesisx.data.source.remote.response

data class UserResponse(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val favorite: List<String> = listOf(),
    val borrowing: List<String>? = listOf()
)
