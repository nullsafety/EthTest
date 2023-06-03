package com.lumi.ethtest

import android.app.Application
import com.lumi.ethtest.di.appModule
import com.lumi.ethtest.di.mainFragmentModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                appModule,
                mainFragmentModule,
            )
        }
    }
}