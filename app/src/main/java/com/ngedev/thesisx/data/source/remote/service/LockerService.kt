package com.ngedev.thesisx.data.source.remote.service


import com.ngedev.thesisx.data.source.remote.network.FirebaseResponse
import com.ngedev.thesisx.data.source.remote.response.LockerResponse
import com.ngedev.thesisx.utils.FirebaseConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class LockerService : FirebaseService() {
    fun modifyLockerKey(key: String): Flow<FirebaseResponse<LockerResponse>> = flow {
        modifyValueInField<String>(
            FirebaseConstant.Collections.LOCKER_COLLECTION,
            FirebaseConstant.Docs.LOCKER_DOCUMENT,
            FirebaseConstant.Fields.PASSWORD_FIELD,
            key
        )

        emitAll(
            getDocument(
                FirebaseConstant.Collections.LOCKER_COLLECTION,
                FirebaseConstant.Docs.LOCKER_DOCUMENT
            )
        )
    }


    fun getKey(): Flow<FirebaseResponse<LockerResponse>> =
        getDocument(
            FirebaseConstant.Collections.LOCKER_COLLECTION,
            FirebaseConstant.Docs.LOCKER_DOCUMENT
        )
}