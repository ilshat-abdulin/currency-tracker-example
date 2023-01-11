package com.air.coins.presentation.coin_list.mapper

import com.air.coins.domain.model.CoinItem
import com.air.coins.presentation.coin_list.model.CoinItemUi
import javax.inject.Inject

class CoinListUiMapper @Inject constructor() {

    fun mapCoinItemToUi(coin: CoinItem) = CoinItemUi(
        fromSymbol = coin.fromSymbol,
        fullName = coin.fullName ?: EMPTY_VALUE,
        currentPrice = "$${coin.currentPrice}",
        lastUpdate = coin.lastUpdate,
        imageUrl = coin.imageUrl ?: EMPTY_VALUE
    )

    companion object {
        private const val EMPTY_VALUE = "n/a"
    }
}