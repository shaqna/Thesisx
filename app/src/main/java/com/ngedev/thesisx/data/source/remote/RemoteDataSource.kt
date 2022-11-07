package com.ngedev.thesisx.data.source.remote

import android.net.Uri
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.LoanResponse
import com.ngedev.thesisx.data.source.remote.response.CupBoardResponse
import com.ngedev.thesisx.data.source.remote.response.ThesisResponse
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import com.ngedev.thesisx.data.source.remote.service.*
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.model.Loan
import kotlinx.coroutines.flow.Flow

class RemoteDataSource(
    private val authService: AuthService,
    private val thesisService: ThesisService,
    private val userService: UserService,
    private val cupBoardService: CupBoardService,
    private val loanService: LoanService
) {
    fun signUp(
        email: String,
        password: String,
        user: User
    ): Flow<FirebaseResponse<UserResponse>> =
        authService.signUp(email, password, user)

    fun signIn(email: String, password: String): Flow<FirebaseResponse<UserResponse>> =
        authService.signIn(email, password)

    fun getAllThesis(): Flow<FirebaseResponse<List<ThesisResponse>>> = thesisService.getAllThesis()

    fun getMyLoan(ids: List<String>): Flow<FirebaseResponse<List<LoanResponse>>> =
        loanService.getMyLoans(ids)

    fun getMyFavorite(thesisIds: List<String>): Flow<FirebaseResponse<List<ThesisResponse>>> =
        thesisService.getMyFavorite(thesisIds)

    fun getThesisById(id: String): Flow<FirebaseResponse<ThesisResponse>> =
        thesisService.getThesisById(id)

    fun getLoanById(id: String): Flow<FirebaseResponse<LoanResponse>> = loanService.getLoanById(id)

    fun searchThesisByTitle(title: String): Flow<FirebaseResponse<List<ThesisResponse>>> =
        thesisService.searchThesisByTitle(title)

    fun getCurrentUser(id: String): Flow<FirebaseResponse<UserResponse>> =
        userService.getUser(id)

    fun getCurrentUserId(): String =
        userService.getCurrentUserId().toString()

    fun addFavoriteThesis(
        thesisId: String
    ): Flow<FirebaseResponse<UserResponse>> =
        userService.addFavoriteThesis(thesisId, getCurrentUserId())

    fun deleteFavoriteThesis(
        thesisId: String,
    ): Flow<FirebaseResponse<UserResponse>> =
        userService.deleteFavoriteThesis(thesisId, getCurrentUserId())


    fun deleteForm(formId: String, userId: String): Flow<FirebaseResponse<Unit>> =
        userService.deleteLoan(formId, userId)

    fun insertForm(
        loan: Loan,
        identityPhoto: Uri,
        userId: String
    ): Flow<FirebaseResponse<LoanResponse>> =
        userService.insertForm(loan, identityPhoto,userId)

    fun updateUsername(
        username: String,
    ): Flow<FirebaseResponse<UserResponse>> =
        userService.updateUsername(username, getCurrentUserId())

    fun updateThesisAvailability(
        state: Boolean,
        thesisId: String
    ): Flow<Unit> =
        thesisService.updateThesisAvailability(state, thesisId)


    fun getKey(): Flow<FirebaseResponse<CupBoardResponse>> = cupBoardService.getKey()

    fun resetPassword(email: String): Flow<FirebaseResponse<Unit>> =
        authService.resetPassword(email)

    fun logOut(): Unit = userService.logOut()

}