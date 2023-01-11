package com.air.core.di

import android.content.Context
import com.air.core.database.CoinDao
import com.air.core.di.modules.DatabaseModule
import com.air.core.di.modules.NetworkModule
import com.air.core.network.services.CoinsService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class
    ]
)
interface CoreComponent {

    fun coinDao(): CoinDao

    fun coinsService(): CoinsService

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): CoreComponent
    }
}