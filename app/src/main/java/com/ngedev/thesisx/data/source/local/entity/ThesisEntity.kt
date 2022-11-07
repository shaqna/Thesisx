package com.ngedev.thesisx.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "thesis")
data class ThesisEntity(
    @PrimaryKey(autoGenerate = false)
    var uid: String = "",
    var title: String ="",

    var author: String = "",

    var year: Int = 0,

    var searchKeyword: String = "",

    var category: String = "",

    var thesisAbstract: String = "",
    var borrowed: Boolean = false
)
