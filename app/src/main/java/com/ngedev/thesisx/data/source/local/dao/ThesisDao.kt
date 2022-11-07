package com.ngedev.thesisx.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngedev.thesisx.data.source.local.entity.ThesisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThesisDao {

    @Query("SELECT * FROM thesis")
    fun selectAllThesis(): Flow<List<ThesisEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThesis(thesis: ThesisEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListThesis(thesis: List<ThesisEntity>)


    @Query("SELECT * FROM thesis WHERE category = :category")
    fun selectAllThesisByCategory(category: String): Flow<List<ThesisEntity>>

    @Query("SELECT * FROM thesis WHERE uid = :id")
    fun selectThesisById(id: String): Flow<ThesisEntity>

    @Query("SELECT * FROM thesis WHERE uid in (:ids) LIMIT 10")
    fun selectAllFavorites(ids: List<String>): Flow<List<ThesisEntity>>

    @Query(
        "SELECT * FROM thesis WHERE title LIKE '%'||:title||'%' OR author LIKE '%'||:title||'%' OR searchKeyword LIKE '%'||:title||'%'"
    )
    fun searchThesis(title: String): Flow<List<ThesisEntity>>

    @Query("DELETE FROM thesis")
    suspend fun clearThesis()


}