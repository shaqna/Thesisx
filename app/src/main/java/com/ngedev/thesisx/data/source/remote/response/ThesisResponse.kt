package com.ngedev.thesisx.data.source.remote.response

import androidx.room.PrimaryKey

data class ThesisResponse(
    val uid: String = "",
    val title: String = "",
    val author: String = "",
    val year: Int = 0,
    val category: String = "",
    val thesisAbstract: String = "",
    val borrowed: Boolean = false
)
