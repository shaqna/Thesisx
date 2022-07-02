package com.ngedev.thesisx.data.source.remote.service

import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.ThesisResponse
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ThesisService : FirebaseService() {
    fun getAllThesis(): Flow<FirebaseResponse<List<ThesisResponse>>> =
        getCollection(FirebaseConstant.Collections.THESIS_COLLECTION)

    fun getMyFavorite(thesisIds: List<String>): Flow<FirebaseResponse<List<ThesisResponse>>> =
        getDocumentsWhereIds(
            FirebaseConstant.Collections.THESIS_COLLECTION,
            FirebaseConstant.Fields.ID_FIELD,
            thesisIds
        )
    fun getMyBorrowing(thesisIds: List<String>): Flow<FirebaseResponse<List<ThesisResponse>>> =
        getDocumentsWhereIds(
            FirebaseConstant.Collections.THESIS_COLLECTION,
            FirebaseConstant.Fields.ID_FIELD,
            thesisIds
        )

    fun getThesisById(id: String): Flow<FirebaseResponse<ThesisResponse>> =
        getDocument(FirebaseConstant.Collections.THESIS_COLLECTION, id)

    fun searchThesisByTitle(title: String): Flow<FirebaseResponse<List<ThesisResponse>>> =
        searchInCollection(
            FirebaseConstant.Collections.THESIS_COLLECTION,
            FirebaseConstant.Fields.TITLE_FIELD,
            title
        )

    fun modifyValueInField(state: Boolean, thesisId: String): Flow<FirebaseResponse<ThesisResponse>> =
        flow {
            modifyValueInField<Boolean>(
                FirebaseConstant.Collections.THESIS_COLLECTION,
                thesisId,
                FirebaseConstant.Fields.BORROWED_STATUS_FIELD,
                state
            )

            emitAll(getDocument<ThesisResponse>(
                FirebaseConstant.Collections.THESIS_COLLECTION,
                thesisId
            ))
        }
}