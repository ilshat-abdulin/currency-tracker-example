package com.air.core.di.modules

import android.content.Context
import androidx.room.Room
import com.air.core.database.AppDatabase
import com.air.core.database.CoinDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        context: Context
    ): AppDatabase = synchronized(this) {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCoinDao(
        database: AppDatabase
    ): CoinDao = database.coinDao()
}