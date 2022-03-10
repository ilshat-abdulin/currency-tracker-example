package com.air.crypto.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.air.crypto.databinding.ActivityCoinDetailBinding
import com.bumptech.glide.Glide

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[CoinViewModel::class.java]

        fromSymbol?.let {
            viewModel.getDetailInfo(it).observe(this, Observer {
                binding.textViewCurrentPrice.text = it.price.toString()
                binding.textViewMinPrice.text = it.lowDay.toString()
                binding.textViewMaxPrice.text = it.highDay.toString()
                binding.textViewMarketLabel.text = it.lastMarket
                binding.textViewLastUpdate.text = it.getFormattedTime()
                binding.textViewFromSymbol.text = it.fromSymbol
                binding.textViewToSymbol.text = it.toSymbol
                Glide.with(this).load(it.getFullImageUrl()).into(binding.imageViewCoinLogo)
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