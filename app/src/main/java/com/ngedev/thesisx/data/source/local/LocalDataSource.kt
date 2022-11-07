package com.ngedev.thesisx.data.source.local

import com.ngedev.thesisx.data.source.local.dao.LoanDao
import com.ngedev.thesisx.data.source.local.dao.ThesisDao
import com.ngedev.thesisx.data.source.local.dao.UserDao
import com.ngedev.thesisx.data.source.local.entity.LoanEntity
import com.ngedev.thesisx.data.source.local.entity.ThesisEntity
import com.ngedev.thesisx.data.source.local.entity.UserEntity

class LocalDataSource(
    private val userDao: UserDao,
    private val thesisDao: ThesisDao,
    private val loanDao: LoanDao
) {
    fun selectThesisById(id: String) = thesisDao.selectThesisById(id)

    fun selectLoanById(id: String) = loanDao.selectLoanById(id)

    fun selectAllFavorites(ids: List<String>) = thesisDao.selectAllFavorites(ids)

    fun selectUser() = userDao.selectCurrentUser()

    fun selectAllLoan() = loanDao.selectAllLoan()

    fun selectAllThesis() = thesisDao.selectAllThesis()

    fun selectAllThesisByCategory(category: String) = thesisDao.selectAllThesisByCategory(category)

    fun searchThesis(title: String) = thesisDao.searchThesis(title)

//    suspend fun clearLoanWHereIsDeleted() = loanDao.clearLoanWHereIsDeleted()

    suspend fun insertLoans(loan: List<LoanEntity>) = loanDao.insertLoans(loan)

    suspend fun insertLoan(loan: LoanEntity) = loanDao.insertLoan(loan)

//    suspend fun updateDeleted(id: String, state: Boolean) = loanDao.updateDeleted(id, state)

    suspend fun deleteLoan(id: String) = loanDao.deleteLoan(id)

    suspend fun insertUser(userEntity: UserEntity) = userDao.insertUser(userEntity)

    suspend fun clearUser() = userDao.clearUser()

    suspend fun insertThesis(thesis: ThesisEntity) = thesisDao.insertThesis(thesis)

    suspend fun insertTheses(thesis: List<ThesisEntity>) = thesisDao.insertListThesis(thesis)

    suspend fun clearThesis() = thesisDao.clearThesis()

    suspend fun clearLoan() = loanDao.clearLoan()


}