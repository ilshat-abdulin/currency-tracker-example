package com.air.crypto.di

import android.app.Application
import com.air.core.di.CoreComponent
import com.air.core.di.scopes.AppScope
import com.air.crypto.CryptoApp
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [
        WorkerModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface ApplicationComponent {
    fun inject(app: CryptoApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            coreComponent: CoreComponent
        ): ApplicationComponent
    }
}