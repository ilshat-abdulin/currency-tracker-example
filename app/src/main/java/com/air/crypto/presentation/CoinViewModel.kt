package com.air.crypto.presentation

import androidx.lifecycle.ViewModel
import com.air.crypto.domain.GetCoinInfoListUseCase
import com.air.crypto.domain.GetCoinInfoUseCase
import com.air.crypto.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase,
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        loadDataUseCase()
    }

    fun refresh() {
        loadDataUseCase()
    }
}