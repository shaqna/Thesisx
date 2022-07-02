package com.ngedev.thesisx.data.mapper

import com.ngedev.thesisx.data.source.local.entity.UserEntity
import com.ngedev.thesisx.domain.model.UserAccount
import com.ngedev.thesisx.data.source.remote.response.UserAccountResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserAccountResponse.toEntity(): UserEntity =
    UserEntity(uid, username, email, favorite, borrowing)

fun UserEntity.toModel(): UserAccount =
    UserAccount(uid, username, email,favorite,borrowing)

fun Flow<UserEntity>.toFlowModel(): Flow<UserAccount> = this.map {
    it.toModel()
}