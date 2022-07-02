package com.ngedev.thesisx.data.source.remote

import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.LockerResponse
import com.ngedev.thesisx.data.source.remote.response.ThesisResponse
import com.ngedev.thesisx.data.source.remote.response.UserAccountResponse
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.data.source.remote.service.AuthService
import com.ngedev.thesisx.data.source.remote.service.LockerService
import com.ngedev.thesisx.data.source.remote.service.ThesisService
import com.ngedev.thesisx.data.source.remote.service.UserService
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(
    private val authService: AuthService,
    private val thesisService: ThesisService,
    private val userService: UserService,
    private val lockerService: LockerService
) {
    fun signUp(
        email: String,
        password: String,
        user: UserAccount
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        authService.signUp(email, password, user)

    fun signIn(email: String, password: String): Flow<FirebaseResponse<UserAccountResponse>> =
        authService.signIn(email, password)

    fun getAllThesis(): Flow<FirebaseResponse<List<ThesisResponse>>> = thesisService.getAllThesis()

    fun getMyFavorite(thesisIds: List<String>): Flow<FirebaseResponse<List<ThesisResponse>>> =
        thesisService.getMyFavorite(thesisIds)

    fun getMyBorrowing(thesisIds: List<String>): Flow<FirebaseResponse<List<ThesisResponse>>> =
        thesisService.getMyBorrowing(thesisIds)

    fun getThesisById(id: String): Flow<FirebaseResponse<ThesisResponse>> =
        thesisService.getThesisById(id)

    fun searchThesisByTitle(title: String): Flow<FirebaseResponse<List<ThesisResponse>>> =
        thesisService.searchThesisByTitle(title)

    fun getCurrentUser(id: String): Flow<FirebaseResponse<UserAccountResponse>> =
        userService.getUser(id)

    fun getCurrentUserId(): String =
        userService.getCurrentUserId().toString()

    fun addFavoriteThesis(
        thesisId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        userService.addFavoriteThesis(thesisId, getCurrentUserId())

    fun deleteFavoriteThesis(
        thesisId: String,
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        userService.deleteFavoriteThesis(thesisId, getCurrentUserId())

    fun addBorrowingThesis(
        thesisId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        userService.addBorrowingThesis(thesisId, getCurrentUserId())

    fun deleteBorrowingThesis(
        thesisId: String,
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        userService.deleteBorrowingThesis(thesisId, getCurrentUserId())

    fun updateUsername(
        username: String,
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        userService.updateUsername(username, getCurrentUserId())

    fun modifyFieldBorrow(
        state: Boolean,
        thesisId: String
    ): Flow<FirebaseResponse<ThesisResponse>> =
        thesisService.modifyValueInField(state, thesisId)

    fun modifyLockerKey(
        key: String
    ): Flow<FirebaseResponse<LockerResponse>> = lockerService.modifyLockerKey(key)

    fun getKey(): Flow<FirebaseResponse<LockerResponse>> = lockerService.getKey()

    fun logOut(): Unit = userService.logOut()

}