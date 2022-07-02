package com.ngedev.thesisx.data.mapper

import com.ngedev.thesisx.data.source.local.entity.ThesisEntity
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.data.source.remote.response.ThesisResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun ThesisEntity.toModel(): Thesis =
    Thesis(uid, title, author, year, category, thesisAbstract, borrowed)

fun ThesisResponse.toEntity(): ThesisEntity =
    ThesisEntity(uid, title, author, year, category, thesisAbstract, borrowed)

fun Flow<ThesisEntity>.toFlowModel(): Flow<Thesis> =
    this.map {
        it.toModel()
    }

fun List<ThesisEntity>.toListModel(): List<Thesis> =
    this.map{
        it.toModel()
    }

fun List<ThesisResponse>.toListEntity(): List<ThesisEntity> =
    this.map {
        it.toEntity()
    }


fun Flow<List<ThesisEntity>>.toListFlowModel() =
    this.map {
        it.toListModel()
    }