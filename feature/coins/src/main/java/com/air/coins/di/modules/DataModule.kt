package com.air.coins.di.modules

import com.air.coins.data.RemoteDataSource
import com.air.coins.data.repository.CoinRepositoryImpl
import com.air.coins.data_source.remote.RemoteDataSourceImpl
import com.air.coins.domain.repository.CoinRepository
import com.air.core.di.scopes.FeatureScope
import dagger.Binds
import dagger.Module

@Module
internal interface DataModule {
    @Binds
    @FeatureScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    @Binds
    @FeatureScope
    fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}