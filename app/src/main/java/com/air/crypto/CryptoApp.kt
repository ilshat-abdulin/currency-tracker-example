package com.air.crypto

import android.app.Application
import androidx.work.Configuration
import com.air.core.di.CoreComponentProvider
import com.air.core.di.DaggerCoreComponent
import com.air.crypto.data.workers.LoadDataWorkerFactory
import com.air.crypto.di.DaggerApplicationComponent
import javax.inject.Inject

class CryptoApp : Application(), Configuration.Provider, CoreComponentProvider {

    @Inject
    lateinit var workerFactory: LoadDataWorkerFactory

    override val coreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    private val component by lazy {
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