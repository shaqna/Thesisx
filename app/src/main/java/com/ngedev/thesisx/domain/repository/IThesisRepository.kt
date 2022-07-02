package com.ngedev.thesisx.domain.repository

import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.domain.Resource
import kotlinx.coroutines.flow.Flow

interface IThesisRepository {

    fun getAllThesis(): Flow<Resource<List<Thesis>>>
    fun getAllThesisByCategory(category: String): Flow<Resource<List<Thesis>>>
    fun getAllFavorites(ids: List<String>): Flow<Resource<List<Thesis>>>
    fun getAllBorrowing(ids: List<String>): Flow<Resource<List<Thesis>>>
    fun getThesisById(id: String): Flow<Resource<Thesis>>
    fun addFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun addBorrowingThesis(id: String): Flow<Resource<Unit>>
    fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun deleteBorrowingThesis(id: String): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<UserAccount>>
    fun getCurrentUserId(): String
    fun getUser(id: String): Flow<Resource<UserAccount>>
    fun searchThesis(title: String): Flow<Resource<List<Thesis>>>
    fun modifyValue(state: Boolean, thesisId: String): Flow<Resource<Unit>>


}