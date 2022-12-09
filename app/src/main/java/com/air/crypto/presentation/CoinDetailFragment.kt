package com.air.crypto.presentation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.crypto.R
import com.air.crypto.databinding.FragmentCoinDetailBinding
import com.air.crypto.domain.model.CoinInfo
import com.bumptech.glide.Glide
import javax.inject.Inject

class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CryptoApp).component
    }

    private val binding by viewBinding(FragmentCoinDetailBinding::bind)

    private val viewModel: CoinViewModel by activityViewModels { viewModelFactory }

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
            viewModel.getDetailInfo(it).observe(viewLifecycleOwner) {
                updateUi(it)
            }
        }
    }

    private fun updateUi(coinInfo: CoinInfo) {
        with(binding) {
            textViewCurrentPrice.text = coinInfo.price.toString()
            textViewMinPrice.text = coinInfo.lowDay.toString()
            textViewMaxPrice.text = coinInfo.highDay.toString()
            textViewMarketLabel.text = coinInfo.lastMarket
            textViewLastUpdate.text = coinInfo.lastUpdate
            textViewFromSymbol.text = coinInfo.fromSymbol
            textViewToSymbol.text = coinInfo.toSymbol
            Glide.with(requireContext()).load(coinInfo.imageUrl).into(imageViewCoinLogo)
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