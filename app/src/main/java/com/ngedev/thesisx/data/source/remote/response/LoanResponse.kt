package com.ngedev.thesisx.data.source.remote.response

data class LoanResponse(
    val uid: String = "",
    val name: String = "",
    val npm: String = "",
    val phone: String = "",
    val date: Long = 0,
    val note: String = "",
    val photoIdentity: String = "",
    val book_title: String = "",
    val author: String = "",
    val year: Int = 0,
    val category: String = "",
    val userId: String = "",
    val thesisId: String = ""
)