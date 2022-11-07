package com.ngedev.thesisx.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Loan(
    var uid: String = "",
    var name: String = "",
    var npm: String = "",
    var phone: String = "",
    var date: Long = 0,
    var note: String = "",
    var photoIdentity: String = "",
    var book_title: String = "",
    var author: String = "",
    var year: Int = 0,
    var category: String = "",
    var userId: String = "",
    var thesisId: String = ""
): Parcelable
