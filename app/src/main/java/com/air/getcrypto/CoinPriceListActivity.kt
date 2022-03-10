package com.air.getcrypto


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.getcrypto.R
import com.air.getcrypto.adapters.CoinInfoAdapter
import com.air.getcrypto.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        val recyclerViewCoinPriceListInfo: RecyclerView = findViewById(R.id.recyclerViewCoinPriceListInfo)
        recyclerViewCoinPriceListInfo.adapter = adapter

        viewModel = ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })

    }


}

