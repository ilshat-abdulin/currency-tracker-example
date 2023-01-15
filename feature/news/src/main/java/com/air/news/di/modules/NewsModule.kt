package com.air.news.di.modules

import com.air.core.di.scopes.FeatureScope
import com.air.news.data.repository.NewsRepositoryImpl
import com.air.news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module

@Module
internal interface NewsModule {
    @Binds
    @FeatureScope
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository
}