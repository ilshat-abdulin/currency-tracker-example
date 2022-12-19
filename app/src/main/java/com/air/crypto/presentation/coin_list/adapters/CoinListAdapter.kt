package com.air.crypto.presentation.coin_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.air.crypto.databinding.ItemCoinInfoBinding
import com.air.crypto.loadImage
import com.air.crypto.presentation.coin_list.model.CoinInfoUi

class CoinListAdapter(private val onCoinClickListener: (CoinInfoUi) -> Unit) :
    ListAdapter<CoinInfoUi, CoinListAdapter.CoinViewHolder>(CoinDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = CoinViewHolder(
        binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onCoinClickListener = onCoinClickListener
    )

    override fun onBindViewHolder(
        holder: CoinViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: CoinViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.bind(getItem(position), payloads)
        }
    }

    inner class CoinViewHolder(
        private val binding: ItemCoinInfoBinding,
        private val onCoinClickListener: (CoinInfoUi) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coinInfo: CoinInfoUi) {
            with(binding) {
                textViewName.text = coinInfo.fromSymbol
                textViewFullName.text = coinInfo.fullName
                textViewPrice.text = coinInfo.currentPrice
                textViewUpdateTime.text = coinInfo.lastUpdate
                imageViewCoinLogo.loadImage(coinInfo.imageUrl)
                itemView.setOnClickListener { onCoinClickListener.invoke(coinInfo) }
            }
        }

        fun bind(coinInfo: CoinInfoUi, payloads: MutableList<Any>) {
            if (payloads.last() !is CoinUpdates) return

            with(binding) {
                when (payloads.last() as CoinUpdates) {
                    is CoinUpdates.PriceUpdate -> {
                        textViewPrice.text = coinInfo.currentPrice
                    }
                    is CoinUpdates.TimeUpdate -> {
                        textViewUpdateTime.text = coinInfo.lastUpdate
                    }
                }
            }
        }
    }

    sealed class CoinUpdates {
        object PriceUpdate : CoinUpdates()
        object TimeUpdate : CoinUpdates()
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}

