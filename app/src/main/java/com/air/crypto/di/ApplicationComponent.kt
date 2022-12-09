package com.air.crypto.di

import android.app.Application
import com.air.crypto.presentation.CoinDetailFragment
import com.air.crypto.presentation.CoinListFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: CoinListFragment)

    fun inject(fragment: CoinDetailFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}