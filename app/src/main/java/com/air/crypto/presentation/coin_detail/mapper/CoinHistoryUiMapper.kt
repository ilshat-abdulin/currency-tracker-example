package com.air.crypto.presentation.coin_detail.mapper

import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.presentation.coin_detail.model.CoinHistoryUi
import com.github.mikephil.charting.data.Entry
import javax.inject.Inject

class CoinHistoryUiMapper @Inject constructor() {

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

}