package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.settings.SettingsInteractor
import com.ngedev.thesisx.domain.usecase.settings.SettingsUseCase
import com.ngedev.thesisx.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

val settingsModule = module {
    factoryOf(::SettingsInteractor) {
        bind<SettingsUseCase>()
    }

    viewModelOf(::SettingsViewModel)
}