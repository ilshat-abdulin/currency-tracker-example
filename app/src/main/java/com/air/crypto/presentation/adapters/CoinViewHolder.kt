package com.air.crypto.presentation.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.air.crypto.R

class CoinViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val textViewIndex: TextView = view.findViewById(R.id.textViewIndex)
    val textViewSymbols: TextView = view.findViewById(R.id.textViewSymbols)
    val textViewPrice: TextView = view.findViewById(R.id.textViewPrice)
    val textViewTimeUpdate: TextView = view.findViewById(R.id.textViewTimeUpdate)
    val imageViewCoinLogo: ImageView = view.findViewById(R.id.imageViewCoinLogo)
}