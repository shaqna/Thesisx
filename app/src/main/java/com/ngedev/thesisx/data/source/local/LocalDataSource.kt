package com.ngedev.thesisx.data.source.local

import com.ngedev.thesisx.data.source.local.dao.ThesisDao
import com.ngedev.thesisx.data.source.local.dao.UserDao
import com.ngedev.thesisx.data.source.local.entity.ThesisEntity
import com.ngedev.thesisx.data.source.local.entity.UserEntity

class LocalDataSource(private val userDao: UserDao, private val thesisDao: ThesisDao) {
    suspend fun clearUser() = userDao.clearUser()

    suspend fun insertUser(userEntity: UserEntity) = userDao.insertUser(userEntity)

    fun selectUser() = userDao.selectCurrentUser()

    fun selectAllThesis() = thesisDao.selectAllThesis()

    suspend fun insertThesis(thesis: ThesisEntity) = thesisDao.insertThesis(thesis)

    suspend fun insertTheses(thesis: List<ThesisEntity>) = thesisDao.insertListThesis(thesis)

    fun selectAllThesisByCategory(category: String) = thesisDao.selectAllThesisByCategory(category)

    fun selectThesisById(id: String) = thesisDao.selectThesisById(id)

    fun selectAllFavorites(ids: List<String>) = thesisDao.selectAllFavorites(ids)

    fun selectAllBorrowing(ids: List<String>) = thesisDao.selectAllBorrowing(ids)

    fun searchThesis(title: String) = thesisDao.searchThesis(title)

    suspend fun clearThesis() = thesisDao.clearThesis()


}