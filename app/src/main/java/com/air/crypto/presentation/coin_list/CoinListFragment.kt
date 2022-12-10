package com.air.crypto.presentation.coin_list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.crypto.R
import com.air.crypto.databinding.FragmentCoinListBinding
import com.air.crypto.domain.model.CoinInfo
import com.air.crypto.presentation.CryptoApp
import com.air.crypto.presentation.ViewModelFactory
import com.air.crypto.presentation.coin_list.adapters.CoinListAdapter
import com.air.crypto.presentation.decorations.MarginDividerItemDecoration
import javax.inject.Inject

class CoinListFragment : Fragment(R.layout.fragment_coin_list) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CryptoApp).component
    }

    private val binding by viewBinding(FragmentCoinListBinding::bind)

    private val viewModel: CoinListViewModel by viewModels { viewModelFactory }

    private var onCoinClickListener: ((CoinInfo) -> Unit)? = null

    private val coinListAdapter by lazy {
        CoinListAdapter {
            onCoinClickListener?.invoke(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeViewState()

        binding.root.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.coinInfoList.collect {
                updateUi(it)
            }
        }
    }

    private fun updateUi(coinList: List<CoinInfo>) {
        coinListAdapter.submitList(coinList)
        binding.root.isRefreshing = false
    }

    private fun setRecyclerView() {
        with(binding.coinListRecyclerView) {
            adapter = coinListAdapter
            itemAnimator = null
            recycledViewPool.setMaxRecycledViews(0, CoinListAdapter.MAX_POOL_SIZE)

            addItemDecoration(
                MarginDividerItemDecoration(
                    horizontal = resources.getDimensionPixelSize(R.dimen.coin_item_horizontal_margin),
                    vertical = resources.getDimensionPixelSize(R.dimen.coin_item_vertical_margin)
                )
            )
        }
    }

    companion object {
        fun newInstance(onCoinClickListener: (CoinInfo) -> Unit) = CoinListFragment().apply {
            this.onCoinClickListener = onCoinClickListener
        }
    }
}

