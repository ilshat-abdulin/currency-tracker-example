package com.air.crypto.data.repository

import com.air.crypto.data.RemoteDataSource
import com.air.core.database.CoinDao
import com.air.crypto.data_source.mapper.CoinMapper
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinItem
import com.air.crypto.domain.repository.CoinRepository
import com.air.core_functional.Either
import com.air.core_functional.Failure
import com.air.crypto.util.repeatWithDelay
import com.air.core_functional.suspendMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val mapper: CoinMapper,
    private val coinDao: CoinDao,
    private val remoteDataSource: RemoteDataSource
) : CoinRepository {

    override fun getCoinList(): Flow<List<CoinItem>> {
        return coinDao.getCoinList().map {
            it.map { model ->
                mapper.mapDbModelToEntity(model)
            }
        }
    }

    override fun getCoinDetail(fromSymbol: String): Flow<CoinItem> {
        return coinDao.getCoin(fromSymbol).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun getCoinHistory(fromSymbol: String): Flow<Either<Failure, CoinHistory>> {
        val coinHistory = remoteDataSource.getCoinHistory(fromSymbol)
        return flow {
            emit(coinHistory)
        }
    }

    override suspend fun loadCoins() = flow {
        val result = remoteDataSource.getTopCoinList().suspendMap { model ->
            val dbModelList = model.coins.map {
                mapper.mapDtoToDbModel(
                    dto = it,
                    fullName = model.coinNamesMap[it.fromSymbol] ?: ""
                )
            }
            coinDao.clearAndInsert(dbModelList)
        }

        emit(result)
    }.repeatWithDelay(10000)
}