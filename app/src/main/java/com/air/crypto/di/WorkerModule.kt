package com.air.crypto.di

import com.air.crypto.data.workers.ChildWorkerFactory
import com.air.crypto.data.workers.LoadDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(LoadDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: LoadDataWorker.Factory): ChildWorkerFactory
}
