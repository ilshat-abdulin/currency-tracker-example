package com.air.crypto.presentation.coin_detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.crypto.R
import com.air.crypto.databinding.FragmentCoinDetailBinding
import com.air.crypto.domain.model.CoinInfo
import com.air.crypto.loadImage
import com.air.crypto.presentation.CryptoApp
import com.air.crypto.presentation.ViewModelFactory
import javax.inject.Inject

class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CryptoApp).component
    }

    private val binding by viewBinding(FragmentCoinDetailBinding::bind)

    private val viewModel: CoinDetailViewModel by viewModels { viewModelFactory }

    private val fromSymbol by lazy {
        requireArguments().getString(EXTRA_FROM_SYMBOL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        if (!requireArguments().containsKey(EXTRA_FROM_SYMBOL)) {
            return
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fromSymbol?.let {
            observeViewState(it)
        }

        binding.imageViewBackFromDetails.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun observeViewState(fromSymbol: String) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getDetailInfo(fromSymbol).collect {
                updateUi(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getCoinHistory(fromSymbol)
        }
    }

    private fun updateUi(coinInfo: CoinInfo) {
        with(binding) {
            textViewCurrentPrice.text = "$ ${coinInfo.currentPrice}"
            textViewMinPerDayPrice.text = "$ ${coinInfo.lowDay}"
            textViewMaxPerDayPrice.text = "$ ${coinInfo.highDay}"
            textViewLastDealMarket.text = coinInfo.lastMarket
            textViewCoinFullName.text = coinInfo.fullName
            textViewCoinName.text = coinInfo.fromSymbol
            textViewUpdateTime.text = coinInfo.lastUpdate
            imageViewCoinLogo.loadImage(coinInfo.imageUrl)
        }
    }


    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newInstance(fromSymbol: String) = CoinDetailFragment().apply {
            arguments = bundleOf(
                EXTRA_FROM_SYMBOL to fromSymbol
            )
        }
    }
}