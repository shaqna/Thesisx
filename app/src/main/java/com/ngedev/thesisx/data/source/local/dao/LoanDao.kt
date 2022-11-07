package com.ngedev.thesisx.data.source.local.dao

import androidx.room.*
import com.ngedev.thesisx.data.source.local.entity.LoanEntity
import com.ngedev.thesisx.data.source.local.entity.ThesisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoan(loan: LoanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoans(loan: List<LoanEntity>)

    @Query("SELECT * FROM loan")
    fun selectAllLoan(): Flow<List<LoanEntity>>

    @Query("SELECT * FROM loan WHERE uid = :id")
    fun selectLoanById(id: String): Flow<LoanEntity>

//    @Query("UPDATE loan SET isDeleted = :isDeleted WHERE uid = :id ")
//    suspend fun updateDeleted(id: String, isDeleted: Boolean)
//
//    @Query("DELETE FROM loan WHERE isDeleted = 1")
//    suspend fun clearLoanWHereIsDeleted()

    @Query("DELETE FROM loan WHERE uid = :id")
    suspend fun deleteLoan(id: String)

    @Query("DELETE FROM loan")
    suspend fun clearLoan()
}