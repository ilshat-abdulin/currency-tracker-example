package com.air.coins.presentation.coin_list.adapters

import androidx.recyclerview.widget.DiffUtil
import com.air.coins.presentation.coin_list.model.CoinItemUi

class CoinDiffCallback : DiffUtil.ItemCallback<CoinItemUi>() {
    override fun areItemsTheSame(oldItem: CoinItemUi, newItem: CoinItemUi): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinItemUi, newItem: CoinItemUi): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: CoinItemUi, newItem: CoinItemUi): Any? {
        if (oldItem.currentPrice != newItem.currentPrice) return CoinListAdapter.CoinUpdates.PriceUpdate
        if (oldItem.lastUpdate != newItem.lastUpdate) return CoinListAdapter.CoinUpdates.TimeUpdate
        return super.getChangePayload(oldItem, newItem)
    }
}