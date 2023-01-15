package com.air.coins.di

import com.air.coins.di.modules.DataModule
import com.air.coins.di.modules.ViewModelModule
import com.air.coins.presentation.coin_list.CoinListFragment
import com.air.core.di.CoreComponent
import com.air.core.di.scopes.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [
        ViewModelModule::class,
        DataModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface CoinsComponent {
    fun inject(fragment: CoinListFragment)

    fun coinDetailComponentFactory(): CoinDetailComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): CoinsComponent
    }
}