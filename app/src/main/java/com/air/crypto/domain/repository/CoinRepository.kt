package com.air.crypto.domain.repository

import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinItem
import com.air.crypto.util.Either
import com.air.crypto.util.Failure
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinList(): Flow<List<CoinItem>>

    fun getCoinDetail(fromSymbol: String): Flow<CoinItem>

    suspend fun getCoinHistory(fromSymbol: String): Flow<Either<Failure, CoinHistory>>

    suspend fun loadCoins(): Flow<Either<Failure, Unit>>
}