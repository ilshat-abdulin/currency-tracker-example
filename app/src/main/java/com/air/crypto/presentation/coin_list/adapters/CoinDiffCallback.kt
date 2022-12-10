package com.air.crypto.presentation.coin_list.adapters

import androidx.recyclerview.widget.DiffUtil
import com.air.crypto.domain.model.CoinInfo

class CoinDiffCallback: DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}