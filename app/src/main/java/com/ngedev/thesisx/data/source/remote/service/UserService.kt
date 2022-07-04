package com.ngedev.thesisx.data.source.remote.service

import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.UserAccountResponse
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class UserService : FirebaseService() {

    fun getCurrentUserId() = auth.currentUser?.uid

    fun getUser(userId: String): Flow<FirebaseResponse<UserAccountResponse>> =
        getDocument(
            FirebaseConstant.Collections.USER_COLLECTION,
            userId
        )

    fun addFavoriteThesis(
        thesisId: String,
        userId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            addStringToArrayValueAtDocField(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId,
                FirebaseConstant.Fields.FAVORITE_FIELD,
                thesisId
            )

            emitAll(
                getDocument<UserAccountResponse>(
                    FirebaseConstant.Collections.USER_COLLECTION,
                    userId
                )
            )
        }

    fun addBorrowingThesis(
        thesisId: String,
        userId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            addStringToArrayValueAtDocField(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId, FirebaseConstant.Fields.BORROWING_FIELD, thesisId
            )
            emitAll(getDocument(FirebaseConstant.Collections.USER_COLLECTION, userId))
        }

    fun deleteFavoriteThesis(
        thesisId: String,
        userId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            removeStringValueInArrayAtDocField(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId,
                FirebaseConstant.Fields.FAVORITE_FIELD,
                thesisId
            )

            emitAll(
                getDocument<UserAccountResponse>(
                    FirebaseConstant.Collections.USER_COLLECTION,
                    userId
                )
            )
        }

    fun deleteBorrowingThesis(
        thesisId: String,
        userId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            removeStringValueInArrayAtDocField(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId,
                FirebaseConstant.Fields.BORROWING_FIELD,
                thesisId
            )

            emitAll(
                getDocument<UserAccountResponse>(
                    FirebaseConstant.Collections.USER_COLLECTION,
                    userId
                )
            )
        }

    fun updateUsername(
        username: String,
        userId: String
    ): Flow<FirebaseResponse<UserAccountResponse>> =
        flow {
            updateFieldInDocument<UserAccount, UserAccountResponse>(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId,
                FirebaseConstant.Fields.USERNAME_FIELD,
                username
            )
        }

    fun logOut(): Unit = signOut()

}