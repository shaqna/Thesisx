package com.ngedev.thesisx.data.mapper

import com.ngedev.thesisx.data.source.remote.response.LockerResponse
import com.ngedev.thesisx.domain.model.LockerKey


fun LockerResponse.toModel(): LockerKey {
    return LockerKey(
        this.key
    )
}