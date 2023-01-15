package com.air.news.di

import com.air.core.di.CoreComponent
import com.air.core.di.scopes.FeatureScope
import com.air.news.di.modules.NewsModule
import com.air.news.presentation.NewsFragment
import dagger.Component

@FeatureScope
@Component(
    modules = [NewsModule::class],
    dependencies = [CoreComponent::class]
)
interface NewsComponent {

    fun inject(fragment: NewsFragment)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): NewsComponent
    }
}