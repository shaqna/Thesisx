package com.ngedev.thesisx.domain.di

import com.ngedev.thesisx.domain.usecase.locker.LockerInteractor
import com.ngedev.thesisx.domain.usecase.locker.LockerUseCase
import com.ngedev.thesisx.ui.locker.LockerViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf


val lockerModule = module {
    factoryOf(::LockerInteractor){bind<LockerUseCase>()}
    viewModelOf(::LockerViewModel)
}