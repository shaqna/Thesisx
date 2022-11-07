package com.ngedev.thesisx.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thesis(
    val uid: String,
    val title: String,
    val author: String,
    val year: Int,
    val searchKeyword: String,
    val category: String,
    val thesisAbstract: String,
    val borrowed: Boolean
): Parcelable
