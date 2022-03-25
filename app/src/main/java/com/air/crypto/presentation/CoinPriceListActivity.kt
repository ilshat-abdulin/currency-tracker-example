package com.air.crypto.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.air.crypto.databinding.ActivityCoinPriceListBinding
import com.air.crypto.presentation.adapters.CoinListAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var coinInfoAdapter: CoinListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as CryptoApp).component
    }

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[CoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setRecyclerView()
        observeViewModel()
        setClickListener()

        binding.root.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setRecyclerView() {
        coinInfoAdapter = CoinListAdapter()
        with(binding.coinListRecyclerView) {
            adapter = coinInfoAdapter
            recycledViewPool.setMaxRecycledViews(0, CoinListAdapter.MAX_POOL_SIZE)
            itemAnimator = null
        }
    }

    private fun observeViewModel() {
        viewModel.coinInfoList.observe(this) {
            coinInfoAdapter.submitList(it)
            binding.root.isRefreshing = false
        }
    }

    private fun setClickListener() {
        coinInfoAdapter.onCoinClickListener =  {
            val intent = CoinDetailActivity.newIntent(
                this@CoinPriceListActivity,
                it.fromSymbol
            )
            startActivity(intent)
        }
    }
}

