package com.ngedev.thesisx.data.mapper

import com.ngedev.thesisx.data.source.local.entity.UserEntity
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.data.source.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun UserResponse.toEntity(): UserEntity =
    UserEntity(uid, username, email, favorite, borrowing)

fun UserEntity.toModel(): User =
    User(uid, username, email,favorite,borrowing)

fun Flow<UserEntity>.toFlowModel(): Flow<User> = this.map {
    it.toModel()
}