package com.ngedev.thesisx.domain.model

data class Thesis(
    val uid: String,
    val title: String,
    val author: String,
    val year: Int,
    val category: String,
    val thesisAbstract: String,
    val borrowed: Boolean
)
