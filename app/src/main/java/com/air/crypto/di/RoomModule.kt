package com.air.crypto.di

import android.app.Application
import androidx.room.Room
import com.air.crypto.data.database.AppDatabase
import com.air.crypto.data.database.CoinPriceInfoDao
import dagger.Module
import dagger.Provides

@Module
object RoomModule {
    @Provides
    @ApplicationScope
    fun provideDatabase(
        application: Application
    ): AppDatabase = synchronized(this) {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideCoinInfoDao(
        database: AppDatabase
    ): CoinPriceInfoDao = database.coinPriceInfoDao()
}