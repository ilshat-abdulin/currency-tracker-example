package com.air.crypto.di

import android.app.Application
import com.air.crypto.presentation.CoinDetailActivity
import com.air.crypto.presentation.CoinPriceListActivity
import com.air.crypto.presentation.CryptoApp
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailActivity)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}