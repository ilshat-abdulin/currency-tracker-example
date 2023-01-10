package com.air.crypto.di

import com.air.core.di.scopes.AppScope
import com.air.crypto.data.RemoteDataSource
import com.air.crypto.data.repository.CoinRepositoryImpl
import com.air.crypto.data_source.remote.RemoteDataSourceImpl
import com.air.crypto.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    @AppScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    @Binds
    @AppScope
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}