package com.air.crypto.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.work.ListenableWorker
import com.air.crypto.presentation.CryptoApp
import com.air.crypto.presentation.coin_detail.CoinDetailFragment
import com.air.crypto.presentation.coin_list.CoinListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.MapKey
import javax.inject.Qualifier
import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class BaseUrl

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        WorkerModule::class,
        RoomModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: CoinListFragment)
    fun inject(fragment: CoinDetailFragment)
    fun inject(app: CryptoApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}