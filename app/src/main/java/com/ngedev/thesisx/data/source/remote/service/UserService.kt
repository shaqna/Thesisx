package com.ngedev.thesisx.data.source.remote.service

import android.net.Uri
import com.ngedev.thesisx.data.helper.first
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.LoanResponse
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.*

class UserService : FirebaseService() {

    fun getCurrentUserId() = auth.currentUser?.uid

    fun getUser(userId: String): Flow<FirebaseResponse<UserResponse>> =
        getDocument(
            FirebaseConstant.Collections.USER_COLLECTION,
            userId
        )

    fun addFavoriteThesis(
        thesisId: String,
        userId: String
    ): Flow<FirebaseResponse<UserResponse>> =
        flow {
            addStringToArrayValueAtDocField(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId,
                FirebaseConstant.Fields.FAVORITE_FIELD,
                thesisId
            )
            emitAll(
                getDocument<UserResponse>(
                    FirebaseConstant.Collections.USER_COLLECTION,
                    userId
                )
            )
        }


   fun insertForm(
       form: Loan,
       imageUri: Uri,
       userId: String
   ): Flow<FirebaseResponse<LoanResponse>> =
       flow {
           val loanId = generateDocumentId(FirebaseConstant.Collections.LOAN_COLLECTION)
           uploadPicture(
               FirebaseConstant.Collections.LOAN_STORAGE,
               loanId,
               imageUri
           ).first { response ->
               when (response) {
                   is FirebaseResponse.Success -> {
                       val imageDownloadUrl = response.data
                       val updateLoanData = form.copy(
                           uid = loanId,
                           photoIdentity = imageDownloadUrl
                       )
                       addStringToArrayValueAtDocField(
                           FirebaseConstant.Collections.USER_COLLECTION,
                           userId,
                           FirebaseConstant.Fields.BORROWING_FIELD,
                           loanId
                       )
                       emitAll(
                           setDocument<Loan, LoanResponse>(
                               FirebaseConstant.Collections.LOAN_COLLECTION,
                               loanId,
                               updateLoanData
                           )
                       )
                   }
                   is FirebaseResponse.Error -> {
                       emit(FirebaseResponse.Error(response.errorMessage))
                   }
                   FirebaseResponse.Empty -> {
                       emit(FirebaseResponse.Empty)
                   }
               }
               true

           }
       }

    fun deleteFavoriteThesis(
        thesisId: String,
        userId: String
    ): Flow<FirebaseResponse<UserResponse>> =
        flow {
            removeStringValueInArrayAtDocField(
                FirebaseConstant.Collections.USER_COLLECTION,
                userId,
                FirebaseConstant.Fields.FAVORITE_FIELD,
                thesisId
            )

            emitAll(
                getDocument<UserResponse>(
                    FirebaseConstant.Collections.USER_COLLECTION,
                    userId
                )
            )
        }

    fun deleteLoan(loanId: String, userId: String): Flow<FirebaseResponse<Unit>> =
        flow {
            deleteDocument(
                FirebaseConstant.Collections.LOAN_COLLECTION,
                loanId
            ).first<Unit, Unit>(this) {
                removeStringValueInArrayAtDocField(
                    FirebaseConstant.Collections.USER_COLLECTION,
                    userId,
                    FirebaseConstant.Fields.BORROWING_FIELD,
                    loanId
                )
                emitAll(deleteFile(FirebaseConstant.Collections.LOAN_STORAGE, loanId))
            }
        }.catch {
            emit(FirebaseResponse.Error(it.message.toString()))
        }

    fun updateUsername(
        username: String,
        userId: String
    ): Flow<FirebaseResponse<UserResponse>> =
        updateFieldInDocument<User, UserResponse>(
            FirebaseConstant.Collections.USER_COLLECTION,
            userId,
            FirebaseConstant.Fields.USERNAME_FIELD,
            username
        )


    fun logOut(): Unit = signOut()

}