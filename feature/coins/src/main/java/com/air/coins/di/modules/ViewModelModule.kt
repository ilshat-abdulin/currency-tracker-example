package com.air.coins.di.modules

import androidx.lifecycle.ViewModel
import com.air.coins.di.ViewModelKey
import com.air.coins.presentation.coin_list.CoinListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    fun bindCoinListViewModel(viewModel: CoinListViewModel): ViewModel
}