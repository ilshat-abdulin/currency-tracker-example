package com.air.crypto.domain

import com.air.crypto.domain.model.CoinInfo
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinInfoList(): Flow<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): Flow<CoinInfo>

    suspend fun getCoinHistory(fromSymbol: String)

    fun loadData()
}