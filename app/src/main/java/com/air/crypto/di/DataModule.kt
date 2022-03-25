package com.air.crypto.di

import android.app.Application
import com.air.crypto.data.database.AppDatabase
import com.air.crypto.data.database.CoinPriceInfoDao
import com.air.crypto.data.repository.CoinRepositoryImpl
import com.air.crypto.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {

        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinPriceInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }
    }
}