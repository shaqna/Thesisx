package com.ngedev.thesisx.domain.usecase.search

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow

class SearchInteractor(private val repository: IThesisRepository): SearchUseCase {
    override fun searchThesis(title: String): Flow<Resource<List<Thesis>>> {
        return repository.searchThesis(title)
    }
}