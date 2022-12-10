package com.air.crypto.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.air.crypto.R
import com.air.crypto.databinding.ActivityMainBinding
import com.air.crypto.presentation.coin_detail.CoinDetailFragment
import com.air.crypto.presentation.coin_list.CoinListFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showCoinList()
    }

    private fun showCoinList() {
        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, CoinListFragment.newInstance {
                showCoinDetails(it.fromSymbol)
            })
        }
    }

    private fun showCoinDetails(fromSymbol: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.main_fragment_container,
                CoinDetailFragment.newInstance(fromSymbol)
            )
            addToBackStack(null)
        }
    }
}