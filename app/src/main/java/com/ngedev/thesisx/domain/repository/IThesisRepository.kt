package com.ngedev.thesisx.domain.repository

import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import kotlinx.coroutines.flow.Flow

interface IThesisRepository {

    fun getAllThesis(): Flow<Resource<List<Thesis>>>
    fun getAllThesisByCategory(category: String): Flow<Resource<List<Thesis>>>
    fun getAllFavorites(ids: List<String>): Flow<Resource<List<Thesis>>>
    fun getThesisById(id: String): Flow<Resource<Thesis>>
    fun addFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun deleteFavoriteThesis(id: String): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<User>>
    fun getCurrentUserId(): String
    fun getUser(id: String): Flow<Resource<User>>
    fun searchThesis(title: String): Flow<Resource<List<Thesis>>>
    fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit>
}