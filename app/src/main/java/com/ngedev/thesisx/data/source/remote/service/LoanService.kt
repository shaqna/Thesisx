package com.ngedev.thesisx.data.source.remote.service

import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.LoanResponse
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.Flow

class LoanService : FirebaseService() {

    fun getMyLoans(loanIds: List<String>): Flow<FirebaseResponse<List<LoanResponse>>> =
        getDocumentsWhereIds(
            FirebaseConstant.Collections.LOAN_COLLECTION,
            FirebaseConstant.Fields.UID_FIELD,
            loanIds
        )

    fun getLoanById(id: String): Flow<FirebaseResponse<LoanResponse>> =
        getDocument(FirebaseConstant.Collections.LOAN_COLLECTION, id)
}