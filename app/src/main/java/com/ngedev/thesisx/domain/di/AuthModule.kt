package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.auth.AuthInteractor
import com.ngedev.thesisx.domain.usecase.auth.AuthUseCase
import com.ngedev.thesisx.ui.auth.login.LoginViewModel
import com.ngedev.thesisx.ui.auth.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authModule = module {

    factoryOf(::AuthInteractor){bind<AuthUseCase>()}
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
