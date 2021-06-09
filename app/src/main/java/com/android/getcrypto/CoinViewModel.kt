package com.android.getcrypto

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.android.getcrypto.api.ApiFactory
import com.android.getcrypto.database.AppDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun loadData(){
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("test", it.toString())
                },{
                    Log.d("test", it.toString())
                })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}