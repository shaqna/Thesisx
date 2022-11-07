package com.ngedev.thesisx.data.source.remote.service


import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.CupBoardResponse
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.Flow

class CupBoardService : FirebaseService() {

    fun getKey(): Flow<FirebaseResponse<CupBoardResponse>> =
        getDocument(
            FirebaseConstant.Collections.LOCKER_COLLECTION,
            FirebaseConstant.Docs.LOCKER_DOCUMENT
        )
}