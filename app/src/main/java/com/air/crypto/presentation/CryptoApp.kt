package com.air.crypto.presentation

import android.app.Application
import androidx.work.Configuration
import com.air.core.di.DaggerCoreComponent
import com.air.crypto.data.workers.LoadDataWorkerFactory
import com.air.crypto.di.DaggerApplicationComponent
import javax.inject.Inject

class CryptoApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: LoadDataWorkerFactory

    private val coreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    val component by lazy {
        DaggerApplicationComponent.factory().create(this, coreComponent)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}