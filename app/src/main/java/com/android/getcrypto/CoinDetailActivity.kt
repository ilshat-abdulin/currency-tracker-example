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
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var textViewCurrentPrice: TextView
    private lateinit var textViewMinPrice: TextView
    private lateinit var textViewMaxPrice: TextView
    private lateinit var textViewMarketLabel: TextView
    private lateinit var textViewLastUpdate: TextView
    private lateinit var textViewFromSymbol: TextView
    private lateinit var textViewToSymbol: TextView
    private lateinit var imageViewCoinLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)
        textViewCurrentPrice = findViewById(R.id.textViewCurrentPrice)
        textViewMinPrice = findViewById(R.id.textViewMinPrice)
        textViewMaxPrice = findViewById(R.id.textViewMaxPrice)
        textViewMarketLabel = findViewById(R.id.textViewMarketLabel)
        textViewLastUpdate = findViewById(R.id.textViewLastUpdate)
        textViewFromSymbol = findViewById(R.id.textViewFromSymbol)
        textViewToSymbol = findViewById(R.id.textViewToSymbol)
        imageViewCoinLogo = findViewById(R.id.imageViewCoinLogo)

        if(!intent.hasExtra(EXTRA_FROM_SYMBOL)){
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]

        if (fromSymbol != null) {
            viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
                textViewCurrentPrice.text = it.price.toString()
                textViewMinPrice.text = it.lowDay.toString()
                textViewMaxPrice.text = it.highDay.toString()
                textViewMarketLabel.text = it.lastMarket
                textViewLastUpdate.text = it.getFormattedTime()
                textViewFromSymbol.text = it.fromSymbol
                textViewToSymbol.text = it.toSymbol
                Picasso.get().load(it.getFullImageUrl()).into(imageViewCoinLogo)
            })
        }
    }

    companion object{
        const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent{
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}