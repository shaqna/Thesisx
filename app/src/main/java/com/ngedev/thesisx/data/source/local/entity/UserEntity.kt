package com.ngedev.thesisx.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val uid: String,
    val username: String,
    val email: String,
    val favorite: List<String> = listOf(),
    val borrowing: List<String> = listOf(),

)

class ListConverter {
    @TypeConverter
    fun toListString(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: List<String>): String {
        val type = object: TypeToken<List<String>>() {}.type
        return Gson().toJson(torrent, type)
    }
}