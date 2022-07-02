package com.ngedev.thesisx.ui.home

import androidx.lifecycle.*
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.usecase.home.HomeUseCase
import com.ngedev.thesisx.utils.Category
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: HomeUseCase) : ViewModel() {

    private val _category = MutableLiveData<String>()
    val category: LiveData<String> = _category

    fun setCategory(category: String) {
        viewModelScope.launch {
            _category.value = category
        }
    }

    fun getThesisByCategory(category: String): LiveData<Resource<List<Thesis>>> {
        return if (category == Category.ALL) {
            useCase.getAllThesis().asLiveData()
        } else {
            useCase.getThesisByCategory(category).asLiveData()
        }
    }

}