package com.air.crypto.presentation.coin_list.adapters

import androidx.recyclerview.widget.DiffUtil
import com.air.crypto.presentation.coin_list.model.CoinInfoUi

class CoinDiffCallback : DiffUtil.ItemCallback<CoinInfoUi>() {
    override fun areItemsTheSame(oldItem: CoinInfoUi, newItem: CoinInfoUi): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfoUi, newItem: CoinInfoUi): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: CoinInfoUi, newItem: CoinInfoUi): Any? {
        if (oldItem.currentPrice != newItem.currentPrice) return CoinListAdapter.CoinUpdates.PriceUpdate
        if (oldItem.lastUpdate != newItem.lastUpdate) return CoinListAdapter.CoinUpdates.TimeUpdate
        return super.getChangePayload(oldItem, newItem)
    }
}