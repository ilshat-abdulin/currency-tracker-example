package com.air.coins.domain.repository

import com.air.coins.domain.model.CoinHistory
import com.air.coins.domain.model.CoinItem
import com.air.core_functional.Either
import com.air.core_functional.Failure
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinList(): Flow<List<CoinItem>>

    fun getCoinDetail(fromSymbol: String): Flow<CoinItem>

    suspend fun getCoinHistory(fromSymbol: String): Flow<Either<Failure, CoinHistory>>

    suspend fun loadCoins(): Flow<Either<Failure, Unit>>
}