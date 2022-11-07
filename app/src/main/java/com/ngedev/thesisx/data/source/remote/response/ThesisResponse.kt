package com.ngedev.thesisx.data.source.remote.response


data class ThesisResponse(
    val uid: String = "",
    val title: String = "",
    val author: String = "",
    val year: Int = 0,
    val searchKeyword: String = "",
    val category: String = "",
    val thesisAbstract: String = "",
    val borrowed: Boolean = false
)
