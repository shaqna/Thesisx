package com.ngedev.thesisx.domain.usecase.search

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {

    fun searchThesis(title: String): Flow<Resource<List<Thesis>>>

}