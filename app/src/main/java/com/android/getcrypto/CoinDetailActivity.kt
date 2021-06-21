package com.android.getcrypto

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.getcrypto.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var coinDetailBinding: ActivityCoinDetailBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coinDetailBinding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(coinDetailBinding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]

        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, Observer {
                coinDetailBinding.textViewCurrentPrice.text = it.price.toString()
                coinDetailBinding.textViewMinPrice.text = it.lowDay.toString()
                coinDetailBinding.textViewMaxPrice.text = it.highDay.toString()
                coinDetailBinding.textViewMarketLabel.text = it.lastMarket
                coinDetailBinding.textViewLastUpdate.text = it.getFormattedTime()
                coinDetailBinding.textViewFromSymbol.text = it.fromSymbol
                coinDetailBinding.textViewToSymbol.text = it.toSymbol
                Picasso.get().load(it.getFullImageUrl()).into(coinDetailBinding.imageViewCoinLogo)
            })
        }
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}