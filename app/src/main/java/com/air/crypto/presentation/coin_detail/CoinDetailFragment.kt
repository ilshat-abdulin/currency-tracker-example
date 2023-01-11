package com.air.crypto.presentation.coin_detail

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.crypto.R
import com.air.crypto.databinding.FragmentCoinDetailBinding
import com.air.crypto.presentation.CryptoApp
import com.air.core_ui.views.PriceValueChartMarker
import com.air.crypto.presentation.ViewModelFactory
import com.air.crypto.presentation.coin_detail.model.CoinHistoryUi
import com.air.core_ui.extensions.loadImage
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.snackbar.Snackbar
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
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
        setLineChartView()
        setUiEventListeners()
        observeUiEffects()

        fromSymbol?.let {
            observeViewState(it)
        }
    }

    private fun setUiEventListeners() {
        binding.imageViewBackFromDetails.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeViewState(fromSymbol: String) {
        viewModel.getCoinHistory(fromSymbol)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                updateUi(it)
            }
        }
    }

    private fun observeUiEffects() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiEffects.collect {
                when (it) {
                    is CoinDetailUiEffects.FailureEffect -> handleError(it.failure)
                }
            }
        }
    }

    private fun updateUi(uiState: CoinDetailUiState) {
        val (loading, coinHistory, coinDetail) = uiState

        with(binding) {
            if (loading) {
                chartLoading.isVisible = true
                chartLoading.setText(getString(R.string.chart_loading))
            } else if (coinHistory.pricesOverTime.isEmpty()) {
                chartLoading.isVisible = false
                chartEmptyTextView.isVisible = true
            } else {
                chartLoading.isVisible = false
                chartEmptyTextView.isVisible = false
            }

            textViewCurrentPrice.text = coinDetail.currentPrice
            textViewMinPerDayPrice.text = coinDetail.lowDay
            textViewMaxPerDayPrice.text = coinDetail.highDay
            textViewLastDealMarket.text = coinDetail.lastMarket
            textViewCoinFullName.text = coinDetail.fullName
            textViewCoinName.text = coinDetail.fromSymbol
            textViewUpdateTime.text = coinDetail.lastUpdate
            imageViewCoinLogo.loadImage(coinDetail.imageUrl)
        }

        updateChart(coinHistory)
    }

    private fun updateChart(coinHistory: CoinHistoryUi) {
        val dataSet = LineDataSet(
            coinHistory.pricesOverTime,
            ""
        ).apply {
            mode = LineDataSet.Mode.LINEAR
            color = ContextCompat.getColor(requireContext(), R.color.white)
            fillDrawable = ContextCompat.getDrawable(requireContext(), R.color.green)
            cubicIntensity = 0.2f
            setDrawIcons(false)
            setDrawFilled(true)
            setDrawCircles(true)
            setDrawValues(false)
        }

        val data = LineData(dataSet)

        binding.coinHistoryChart.apply {
            axisLeft.axisMinimum = coinHistory.lowestValue - (coinHistory.lowestValue * 0.05f)
            axisLeft.axisMaximum = coinHistory.highestValue + (coinHistory.highestValue * 0.05f)
            invalidate()
            setData(data)
            notifyDataSetChanged()
        }
    }

    private fun setLineChartView() {
        val priceValueChartMarker = PriceValueChartMarker(requireContext())

        binding.coinHistoryChart.apply {
            priceValueChartMarker.chartView = this
            marker = priceValueChartMarker

            isAutoScaleMinMaxEnabled = true
            description.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.apply {
                setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
                setDrawGridLines(false)
                axisMinimum = 0f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "$$value"
                    }
                }
            }
            xAxis.apply {
                isEnabled = true
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                labelCount = 8
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val stamp = Timestamp(value.toLong() * 1000)
                        val date = Date(stamp.time)
                        val pattern = "HH:mm"
                        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                        sdf.timeZone = TimeZone.getDefault()
                        return sdf.format(date)
                    }
                }
            }

            setTouchEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(false)
            invalidate()
        }
    }

    private fun handleError(failure: com.air.core_functional.Failure) {
        val message = when (failure) {
            is com.air.core_functional.Failure.NetworkUnavailable -> getString(R.string.network_error)
            else -> getString(R.string.error_message)
        }
        showSnackbar(message)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_FROM_SYMBOL = "fSym"

        fun newInstance(fromSymbol: String) = CoinDetailFragment().apply {
            arguments = bundleOf(
                EXTRA_FROM_SYMBOL to fromSymbol
            )
        }
    }
}