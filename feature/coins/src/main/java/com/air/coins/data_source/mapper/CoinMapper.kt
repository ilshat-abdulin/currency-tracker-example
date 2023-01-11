package com.air.coins.data_source.mapper

import com.air.core.database.model.CoinDbModel
import com.air.core.network.model.CoinHistoryDataDto
import com.air.core.network.model.CoinDto
import com.air.core.network.model.CoinJsonContainerDto
import com.air.core.network.model.CoinNamesListDto
import com.air.coins.domain.model.CoinHistory
import com.air.coins.domain.model.CoinItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

internal class CoinMapper @Inject constructor() {
    fun mapDtoToDbModel(dto: CoinDto, fullName: String) = CoinDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl,
        fullName = fullName
    )



    fun mapDbModelToEntity(dbModel: CoinDbModel) = CoinItem(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        currentPrice = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl,
        fullName = dbModel.fullName
    )



    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    fun mapCoinHistoryDtoToEntity(dtoList: List<CoinHistoryDataDto>) =
        CoinHistory(
            lowestPrice = dtoList.minOf { it.close }.toFloat(),
            highestPrice = dtoList.maxOf { it.close }.toFloat(),
            pricesOverTime = dtoList.associate { it.time to it.close }
        )

    fun mapCoinNamesToMap(namesListDto: CoinNamesListDto): Map<String, String> {
        return namesListDto.names?.associate {
            it.coinName?.name.orEmpty() to it.coinName?.fullName.orEmpty()
        } ?: emptyMap()
    }

    fun mapJsonContainerToCoinList(jsonContainer: CoinJsonContainerDto): List<CoinDto> {
        val converter = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        val result = mutableListOf<CoinDto>()
        val jsonObject = jsonContainer.json ?: return result

        for (coins in jsonObject.values) {
            for (info in coins.jsonObject.values) {
                val priceInfo = converter.decodeFromJsonElement(
                    CoinDto.serializer(),
                    info.jsonObject
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}