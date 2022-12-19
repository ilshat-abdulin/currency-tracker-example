package com.air.crypto.data.workers

import android.content.Context
import androidx.work.*
import com.air.crypto.data.database.CoinPriceInfoDao
import com.air.crypto.data.mappers.CoinInfoMapper
import com.air.crypto.data.network.ApiService
import javax.inject.Inject

class LoadDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinPriceInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinInfoMapper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return Result.success()
    }

    companion object {

        const val NAME = "LoadDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<LoadDataWorker>().setConstraints(
                Constraints.Builder()
                    //.setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
        }
    }

    class Factory @Inject constructor(
        private val coinInfoDao: CoinPriceInfoDao,
        private val apiService: ApiService,
        private val mapper: CoinInfoMapper
    ) : ChildWorkerFactory {

        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return LoadDataWorker(
                context,
                workerParameters,
                coinInfoDao,
                apiService,
                mapper
            )
        }
    }
}