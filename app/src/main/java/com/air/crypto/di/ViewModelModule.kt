package com.air.crypto.di

import androidx.lifecycle.ViewModel
import com.air.crypto.presentation.coin_detail.CoinDetailViewModel
import com.air.crypto.presentation.coin_list.CoinListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CoinListViewModel::class)
    fun bindCoinListViewModel(viewModel: CoinListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CoinDetailViewModel::class)
    fun bindCoinDetailViewModel(viewModel: CoinDetailViewModel): ViewModel
}