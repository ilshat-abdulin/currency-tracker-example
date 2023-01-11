package com.air.coins.di

import com.air.coins.presentation.coin_detail.CoinDetailFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent()
interface CoinDetailComponent {
    fun inject(fragment: CoinDetailFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance @FromSymbol fromSymbol: String
        ): CoinDetailComponent
    }
}