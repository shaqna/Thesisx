package com.ngedev.thesisx.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ngedev.thesisx.data.source.local.dao.ThesisDao
import com.ngedev.thesisx.data.source.local.dao.UserDao
import com.ngedev.thesisx.data.source.local.entity.*

@Database(entities = [UserEntity::class,ThesisEntity::class], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun thesisDao(): ThesisDao
}