package com.air.crypto.presentation.coin_detail

import androidx.lifecycle.ViewModel
import com.air.crypto.domain.usecase.GetCoinHistoryUseCase
import com.air.crypto.domain.usecase.GetCoinInfoUseCase
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase
) : ViewModel() {
    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    suspend fun getCoinHistory(fSym: String) = getCoinHistoryUseCase(fSym)
}