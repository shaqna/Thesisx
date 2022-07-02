package com.ngedev.thesisx

import android.app.Application
import com.ngedev.thesisx.domain.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ThesisApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ThesisApp)
            loadKoinModules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    dataSourceModule,
                    serviceModule
                )
            )
        }
    }
}