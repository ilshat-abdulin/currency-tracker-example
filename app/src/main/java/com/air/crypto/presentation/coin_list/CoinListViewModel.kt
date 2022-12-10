package com.air.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import com.air.crypto.domain.GetCoinInfoListUseCase
import com.air.crypto.domain.LoadDataUseCase
import javax.inject.Inject

class CoinListViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    init {
        loadDataUseCase()
    }

    fun refresh() {
        loadDataUseCase()
    }
}