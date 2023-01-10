package com.air.crypto.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.ListenableWorker
import com.air.core.di.CoreComponent
import com.air.core.di.scopes.AppScope
import com.air.crypto.presentation.CryptoApp
import com.air.crypto.presentation.coin_detail.CoinDetailFragment
import com.air.crypto.presentation.coin_list.CoinListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@AppScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface ApplicationComponent {

    fun inject(fragment: CoinListFragment)
    fun inject(fragment: CoinDetailFragment)
    fun inject(app: CryptoApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
            coreComponent: CoreComponent
        ): ApplicationComponent
    }
}