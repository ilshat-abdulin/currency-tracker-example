package com.air.crypto.di

import com.air.crypto.data.repository.CoinRepositoryImpl
import com.air.crypto.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideJsonConverter(): Json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}