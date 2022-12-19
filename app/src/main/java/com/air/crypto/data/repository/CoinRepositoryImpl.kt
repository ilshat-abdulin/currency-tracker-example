package com.air.crypto.data.repository

import android.util.Log
import com.air.crypto.data.database.CoinPriceInfoDao
import com.air.crypto.data.mappers.CoinInfoMapper
import com.air.crypto.data.network.ApiService
import com.air.crypto.domain.RequestResult
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinInfo
import com.air.crypto.domain.repository.CoinRepository
import com.air.crypto.repeatWithDelay
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val mapper: CoinInfoMapper,
    private val coinPriceInfoDao: CoinPriceInfoDao,
    private val apiService: ApiService,
    private val coinInfoDao: CoinPriceInfoDao,
) : CoinRepository {

    override fun getCoinInfoList(): Flow<List<CoinInfo>> {
        return coinPriceInfoDao.getPriceList().map {
            it.map { model ->
                mapper.mapDbModelToEntity(model)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): Flow<CoinInfo> {
        return coinPriceInfoDao.getPriceInfoAboutCoin(fromSymbol).map {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun getCoinHistory(fromSymbol: String): Flow<CoinHistory> {
        val list = mutableListOf<Entry>()
        var coinHistory = CoinHistory()
        val response = apiService.getCoinHistory(fromSymbol).data?.data.orEmpty()

        coinHistory = coinHistory.copy(lowestPrice = response.minOf {
            it.close
        }.toFloat(), highestPrice = response.maxOf {
            it.close
        }.toFloat())

        response.forEach {
            list.add(Entry(it.time.toFloat(), it.close.toFloat()))
            coinHistory = coinHistory.copy(allPricesPerTime = list.toList())
        }
        return flow {
            emit(coinHistory)
        }
    }

    override suspend fun loadData() = flow<RequestResult> {

        withContext(Dispatchers.IO) {
            val topCoins = apiService.getTopCoinsInfo()
            val coinsMap = mapper.mapNamesListToMap(topCoins)
            val jsonContainer =
                apiService.getFullPriceList(fSyms = coinsMap.keys.joinToString(","))
            val coinInfoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
            val dbModelList = coinInfoList.map {
                mapper.mapDtoToDbModel(
                    dto = it,
                    fullName = coinsMap[it.fromSymbol] ?: ""
                )
            }
            coinInfoDao.clearAndInsert(dbModelList)
        }

        emit(RequestResult.Success)
        Log.d("RequestResult", "isConnected")
    }.catch { cause ->
        Log.d("RequestResult", cause.toString())
        emit(RequestResult.Failure(cause))
    }.repeatWithDelay(10000)
}