package com.air.crypto.di

import com.air.crypto.data.repository.CoinRepositoryImpl
import com.air.crypto.domain.CoinRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
}