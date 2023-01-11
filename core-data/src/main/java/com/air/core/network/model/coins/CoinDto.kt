package com.air.core.network.model.coins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    @SerialName("TYPE")
    val type: String?,
    @SerialName("MARKET")
    val market: String?,
    @SerialName("FROMSYMBOL")
    val fromSymbol: String,
    @SerialName("TOSYMBOL")
    val toSymbol: String?,
    @SerialName("FLAGS")
    val flags: String?,
    @SerialName("PRICE")
    val price: String?,
    @SerialName("LASTUPDATE")
    val lastUpdate: Long?,
    @SerialName("LASTVOLUME")
    val lastVolume: String?,
    @SerialName("LASTVOLUMETO")
    val lastVolumeTo: String?,
    @SerialName("LASTTRADEID")
    val lastTradeId: String?,
    @SerialName("VOLUMEDAY")
    val volumeDay: String?,
    @SerialName("VOLUMEDAYTO")
    val volumeDayTo: String?,
    @SerialName("VOLUME24HOUR")
    val volume24Hour: String?,
    @SerialName("VOLUME24HOURTO")
    val volume24HourTo: String?,
    @SerialName("OPENDAY")
    val openDay: String?,
    @SerialName("HIGHDAY")
    val highDay: String?,
    @SerialName("LOWDAY")
    val lowDay: String?,
    @SerialName("OPEN24HOUR")
    val open24Hour: String?,
    @SerialName("HIGH24HOUR")
    val high24Hour: String?,
    @SerialName("LOW24HOUR")
    val low24Hour: String?,
    @SerialName("LASTMARKET")
    val lastMarket: String?,
    @SerialName("VOLUMEHOUR")
    val volumeHour: String?,
    @SerialName("VOLUMEHOURTO")
    val volumeHourTo: String?,
    @SerialName("OPENHOUR")
    val openHour: String?,
    @SerialName("HIGHHOUR")
    val highHour: String?,
    @SerialName("LOWHOUR")
    val lowHour: String?,
    @SerialName("TOPTIERVOLUME24HOUR")
    val topTierVolume24Hour: String?,
    @SerialName("TOPTIERVOLUME24HOURTO")
    val topTierVolume24HourTo: String?,
    @SerialName("CHANGE24HOUR")
    val change24Hour: String?,
    @SerialName("CHANGEPCT24HOUR")
    val changePCT24Hour: String?,
    @SerialName("CHANGEDAY")
    val changeDay: String?,
    @SerialName("CHANGEPCTDAY")
    val changePCTDay: String?,
    @SerialName("SUPPLY")
    val supply: String?,
    @SerialName("MKTCAP")
    val mktCap: String?,
    @SerialName("TOTALVOLUME24H")
    val totalVolume24Hour: String?,
    @SerialName("TOTALVOLUME24HTO")
    val totalVolume24HourTo: String?,
    @SerialName("TOTALTOPTIERVOLUME24H")
    val totalTopTierVolume24Hour: String?,
    @SerialName("TOTALTOPTIERVOLUME24HTO")
    val totalTopTierVolume24HourTo: String?,
    @SerialName("IMAGEURL")
    val imageUrl: String?
)