package com.air.crypto.di

import android.app.Application
import androidx.room.Room
import com.air.crypto.data_source.local.database.AppDatabase
import com.air.crypto.data_source.local.database.CoinDao
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
    fun provideCoinDao(
        database: AppDatabase
    ): CoinDao = database.coinDao()
}