package com.ngedev.thesisx.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loan")
data class LoanEntity(
    @PrimaryKey(autoGenerate = false)
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

    var thesisId: String = "",
)
