package com.ngedev.thesisx.domain.usecase.form

import android.net.Uri
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LoanFormUseCase {
    fun uploadForm(form: Loan, imageUri: Uri): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<User>>
    fun updateThesisAvailability(state: Boolean, thesisId: String): Flow<Unit>

}