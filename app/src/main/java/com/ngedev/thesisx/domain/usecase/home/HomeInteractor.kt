package com.ngedev.thesisx.domain.usecase.home

import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.repository.IThesisRepository
import kotlinx.coroutines.flow.Flow


class HomeInteractor(private val repository: IThesisRepository): HomeUseCase {
    override fun getAllThesis(): Flow<Resource<List<Thesis>>> {
        return repository.getAllThesis()
    }

    override fun getThesisByCategory(category: String): Flow<Resource<List<Thesis>>> {
        return repository.getAllThesisByCategory(category)
    }

}