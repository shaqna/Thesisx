package com.ngedev.thesisx.domain.usecase.home

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import kotlinx.coroutines.flow.Flow

interface HomeUseCase {
    fun getAllThesis(): Flow<Resource<List<Thesis>>>

    fun getThesisByCategory(category: String): Flow<Resource<List<Thesis>>>

}