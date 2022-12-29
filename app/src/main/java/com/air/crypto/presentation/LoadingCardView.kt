package com.air.crypto.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import com.air.crypto.R
import com.google.android.material.card.MaterialCardView

class LoadingCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val progressBar: ProgressBar
    private val textView: TextView

    init {
        val root = LayoutInflater.from(context).inflate(R.layout.loading_card, this, true)
        textView = root.findViewById(R.id.cardTextView)
        progressBar = root.findViewById(R.id.cardProgressBar)
    }

    fun setText(text: String?) {
        textView.text = text
    }
}