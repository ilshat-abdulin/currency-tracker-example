package com.air.crypto.presentation.coin_list.mapper

import com.air.crypto.domain.model.CoinInfo
import com.air.crypto.presentation.coin_list.model.CoinInfoUi
import javax.inject.Inject

class UiCoinInfoMapper @Inject constructor() {

    fun mapCoinInfoToUi(coinInfo: CoinInfo) = CoinInfoUi(
        fromSymbol = coinInfo.fromSymbol,
        fullName = coinInfo.fullName ?: EMPTY_VALUE,
        currentPrice = "$${coinInfo.currentPrice}",
        lastUpdate = coinInfo.lastUpdate,
        imageUrl = coinInfo.imageUrl ?: EMPTY_VALUE
    )

    companion object {
        private const val EMPTY_VALUE = "n/a"
    }
}