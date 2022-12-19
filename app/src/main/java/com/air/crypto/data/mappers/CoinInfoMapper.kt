package com.air.crypto.data.mappers

import com.air.crypto.data.database.model.CoinInfoDbModel
import com.air.crypto.data.network.model.CoinInfoDto
import com.air.crypto.data.network.model.CoinInfoJsonContainerDto
import com.air.crypto.data.network.model.CoinNamesListDto
import com.air.crypto.domain.model.CoinInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinInfoMapper @Inject constructor(
    private val converter: Json
) {

    fun mapDtoToDbModel(dto: CoinInfoDto, fullName: String) = CoinInfoDbModel(
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

    fun mapNamesListToMap(namesListDto: CoinNamesListDto): Map<String, String> {
        if (namesListDto.names.isNullOrEmpty()) return emptyMap()
        return namesListDto.names.associate {
            it.coinName?.name.orEmpty() to it.coinName?.fullName.orEmpty()
        }
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
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

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result

        for (coins in jsonObject.values) {
            for (info in coins.jsonObject.values) {
                val priceInfo = converter.decodeFromJsonElement(
                    CoinInfoDto.serializer(),
                    info.jsonObject
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}