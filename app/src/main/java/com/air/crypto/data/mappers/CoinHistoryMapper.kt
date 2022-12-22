package com.air.crypto.data.mappers

import com.air.crypto.data.network.model.CoinHistoryDataDto
import com.air.crypto.domain.model.CoinHistory
import javax.inject.Inject

class CoinHistoryMapper @Inject constructor() {
    fun mapCoinHistoryDataDtoToEntity(dtoList: List<CoinHistoryDataDto>) = CoinHistory(
        lowestPrice = dtoList.minOf { it.close }.toFloat(),
        highestPrice = dtoList.maxOf { it.close }.toFloat(),
        pricesOverTime = dtoList.associate { it.time to it.close }
    )
}