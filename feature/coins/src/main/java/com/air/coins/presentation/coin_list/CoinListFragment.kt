package com.air.coins.presentation.coin_list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.coins.R
import com.air.coins.databinding.FragmentCoinListBinding
import com.air.coins.di.CoinsComponentHolder
import com.air.coins.presentation.ViewModelFactory
import com.air.coins.presentation.coin_detail.CoinDetailFragment
import com.air.coins.presentation.coin_list.adapters.CoinListAdapter
import com.air.core.di.CoreComponentProvider
import com.air.core_functional.Failure
import com.air.core_ui.base.BaseFragment
import com.air.core_ui.rv_decorations.MarginDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CoinListFragment :
    BaseFragment<FragmentCoinListBinding, CoinListViewModel>(R.layout.fragment_coin_list) {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val coreComponent by lazy {
        (requireActivity().application as CoreComponentProvider).coreComponent
    }

    private val coinsComponentHolder: CoinsComponentHolder by activityViewModels()

    override val binding by viewBinding(FragmentCoinListBinding::bind)

    override val viewModel: CoinListViewModel by viewModels { viewModelFactory }

    private val coinListAdapter by lazy {
        CoinListAdapter {
            showCoinDetails(it.fromSymbol)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        coinsComponentHolder
            .getComponent(coreComponent)
            .inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeUiState()
        observeUiEffects()
        setUiEventListeners()
    }

    private fun setUiEventListeners() {
        binding.root.setOnRefreshListener {
            binding.root.isRefreshing = false
            viewModel.refresh()
        }
    }

    private fun observeUiState() {
        viewModel.uiState
            .onEach { updateUi(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeUiEffects() {
        viewModel.uiEffects
            .onEach {
                when (it) {
                    is CoinListUiEffects.FailureEffect -> handleError(it.failure)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateUi(uiState: CoinListUiState) {
        val (loading, coins) = uiState

        with(binding) {
            if (loading) {
                coinsLoading.isVisible = true
                coinsLoading.setText(getString(R.string.coins_loading))
            } else if (coins.isEmpty()) {
                coinsLoading.isVisible = false
                coinsEmptyTextView.isVisible = true
            } else {
                coinsLoading.isVisible = false
                coinsEmptyTextView.isVisible = false
            }
        }

        coinListAdapter.submitList(uiState.coins)
    }

    private fun handleError(failure: Failure) {
        val message = when (failure) {
            is Failure.NetworkUnavailable -> getString(R.string.network_error)
            else -> getString(R.string.error_message)
        }
        showSnackbar(message)
    }

    private fun setRecyclerView() {
        with(binding.coinListRecyclerView) {
            adapter = coinListAdapter
            recycledViewPool.setMaxRecycledViews(0, CoinListAdapter.MAX_POOL_SIZE)

            addItemDecoration(
                MarginDividerItemDecoration(
                    horizontal = resources.getDimensionPixelSize(R.dimen.coin_item_horizontal_margin),
                    vertical = resources.getDimensionPixelSize(R.dimen.coin_item_vertical_margin)
                )
            )
        }
    }

    private fun showCoinDetails(fromSymbol: String) {
        val args = bundleOf(
            CoinDetailFragment.EXTRA_FROM_SYMBOL to fromSymbol
        )
        findNavController().navigate(R.id.action_fragment_coins_to_fragment_coin_detail, args)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = CoinListFragment()
    }
}

