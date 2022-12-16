package com.air.crypto.data.repository

import android.app.Application
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.air.crypto.data.database.CoinPriceInfoDao
import com.air.crypto.data.mappers.CoinInfoMapper
import com.air.crypto.data.network.ApiService
import com.air.crypto.data.workers.LoadDataWorker
import com.air.crypto.domain.CoinRepository
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinInfo
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val mapper: CoinInfoMapper,
    private val coinPriceInfoDao: CoinPriceInfoDao,
    private val application: Application,
    private val apiService: ApiService
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

    override suspend fun getCoinHistory(fromSymbol: String): CoinHistory {
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
        return coinHistory
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            LoadDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            LoadDataWorker.makeRequest()
        )
    }
}