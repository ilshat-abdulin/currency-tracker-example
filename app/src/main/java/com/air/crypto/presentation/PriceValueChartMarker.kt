package com.air.crypto.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.air.crypto.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class PriceValueChartMarker(
    context: Context,
    attrs: AttributeSet? = null,
    layoutResource: Int = R.layout.price_value_chart_marker
) : MarkerView(context, layoutResource) {

    private var textViewClosePriceValue: TextView = findViewById(R.id.textViewClosePriceValue)
    private var textViewClosePriceTime: TextView = findViewById(R.id.textViewClosePriceTime)

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        if (e is CandleEntry) {
            textViewClosePriceValue.text = e.high.toString()
        } else {
            textViewClosePriceValue.text = e?.y.toString()
            textViewClosePriceTime.apply {
                val timestamp = e?.x?.toLong() ?: 1
                val stamp = Timestamp(timestamp * 1000)
                val date = Date(stamp.time)
                val pattern = "HH:mm"
                val sdf = SimpleDateFormat(pattern, Locale.getDefault())
                sdf.timeZone = TimeZone.getDefault()
                text = sdf.format(date)
            }
        }
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-width).toFloat(), (-height / 2).toFloat())
    }
}