package com.ngedev.thesisx.ui.form

import android.net.Uri
import androidx.lifecycle.*
import com.ngedev.thesisx.domain.Resource
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.domain.model.User
import com.ngedev.thesisx.domain.usecase.form.LoanFormUseCase

class LoanFormViewModel(private val useCase: LoanFormUseCase) : ViewModel() {
    private val imageUri = MutableLiveData<Uri>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _thesis = MutableLiveData<Thesis>()

    fun setThesis(thesis: Thesis?) {
        thesis?.let {
            this._thesis.value = it
        }
    }

    fun getThesis(): LiveData<Thesis> = _thesis

    fun setImage(image: Uri) {
        this.imageUri.value = image
    }

    fun setCurrentUser(user: User) {
        _user.postValue(user)
    }


    fun getCurrentUser(): LiveData<Resource<User>> = useCase.getCurrentUser().asLiveData()

    fun getImageUri(): LiveData<Uri> = imageUri

    fun uploadForm(form: Loan, imageUri: Uri): LiveData<Resource<Unit>> =
        useCase.uploadForm(form, imageUri).asLiveData()

    fun changeStateBorrow(state: Boolean, id: String): LiveData<Unit> {
        return useCase.updateThesisAvailability(state, id).asLiveData()
    }



}