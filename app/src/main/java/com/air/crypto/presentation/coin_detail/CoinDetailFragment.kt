package com.air.crypto.presentation.coin_detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.crypto.R
import com.air.crypto.databinding.FragmentCoinDetailBinding
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.model.CoinInfo
import com.air.crypto.getQueryAsFlow
import com.air.crypto.loadImage
import com.air.crypto.presentation.CryptoApp
import com.air.crypto.presentation.PriceValueChartMarker
import com.air.crypto.presentation.ViewModelFactory
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

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
            val coinHistory = viewModel.getCoinHistory(fromSymbol)
            updateChart(coinHistory)
        }
    }

    private fun updateChart(coinHistory: CoinHistory) {
        val dataSet = LineDataSet(
            coinHistory.allPricesPerTime,
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
            animateX(250)
            axisLeft.axisMinimum = coinHistory.lowestPrice
            axisLeft.axisMaximum = coinHistory.highestPrice
            invalidate()
            setData(data)
            notifyDataSetChanged()
        }
    }

    private fun updateUi(coinInfo: CoinInfo) {
        with(binding) {
            textViewCurrentPrice.text = "$${coinInfo.currentPrice}"
            textViewMinPerDayPrice.text = "$${coinInfo.lowDay}"
            textViewMaxPerDayPrice.text = "$${coinInfo.highDay}"
            textViewLastDealMarket.text = coinInfo.lastMarket
            textViewCoinFullName.text = coinInfo.fullName
            textViewCoinName.text = coinInfo.fromSymbol
            textViewUpdateTime.text = coinInfo.lastUpdate
            imageViewCoinLogo.loadImage(coinInfo.imageUrl)
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
            setOnChartValueSelectedListener(setOnChartSelectedListener())
            invalidate()
        }
    }

    private fun setOnChartSelectedListener() = object : OnChartValueSelectedListener {
        override fun onValueSelected(e: Entry?, h: Highlight?) {
            Log.d("OnChartValue", "time: ${e?.x?.toLong().toString()}, price: ${e?.y.toString()}")
        }

        override fun onNothingSelected() {}
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