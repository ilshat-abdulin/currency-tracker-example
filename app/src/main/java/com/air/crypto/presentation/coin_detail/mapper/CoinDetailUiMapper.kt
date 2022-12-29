package com.air.crypto.presentation.coin_detail.mapper

import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinItem
import com.air.crypto.presentation.coin_detail.model.CoinDetailUi
import com.air.crypto.presentation.coin_detail.model.CoinHistoryUi
import com.github.mikephil.charting.data.Entry
import javax.inject.Inject

class CoinDetailUiMapper @Inject constructor() {

    fun mapCoinHistoryToUi(coinHistory: CoinHistory): CoinHistoryUi {
        return CoinHistoryUi(
            pricesOverTime = coinHistory.pricesOverTime.map {
                Entry(
                    it.key.toFloat(),
                    it.value.toFloat()
                )
            },
            lowestValue = coinHistory.lowestPrice,
            highestValue = coinHistory.highestPrice
        )
    }

    fun mapCoinItemToUi(coinInfo: CoinItem) = CoinDetailUi(
        fromSymbol = coinInfo.fromSymbol,
        fullName = coinInfo.fullName.orEmpty(),
        currentPrice = "$${coinInfo.currentPrice}",
        lowDay = "$${coinInfo.lowDay}",
        highDay = "$${coinInfo.highDay}",
        lastMarket = coinInfo.lastMarket.orEmpty(),
        lastUpdate = coinInfo.lastUpdate,
        imageUrl = coinInfo.imageUrl.orEmpty()
    )
}