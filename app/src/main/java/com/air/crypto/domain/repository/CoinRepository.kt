package com.air.crypto.domain.repository

import com.air.crypto.domain.RequestResult
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinInfo
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinInfoList(): Flow<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): Flow<CoinInfo>

    suspend fun getCoinHistory(fromSymbol: String): CoinHistory

    suspend fun loadData(): Flow<RequestResult>
}