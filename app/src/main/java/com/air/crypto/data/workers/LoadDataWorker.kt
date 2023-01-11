package com.air.crypto.data.workers

import android.content.Context
import androidx.work.*
import com.air.core.database.CoinDao
import com.air.core.network.services.CoinsService
import javax.inject.Inject

class LoadDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val coinDao: CoinDao,
    private val apiService: CoinsService,
    //private val mapper: CoinMapper
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return Result.success()
    }

    companion object {

        const val NAME = "LoadDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<LoadDataWorker>().setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).build()
        }
    }

    class Factory @Inject constructor(
        private val coinDao: CoinDao,
        private val apiService: CoinsService,
        //private val mapper: CoinMapper
    ) : ChildWorkerFactory {

        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return LoadDataWorker(
                context,
                workerParameters,
                coinDao,
                apiService,
            //    mapper
            )
        }
    }
}