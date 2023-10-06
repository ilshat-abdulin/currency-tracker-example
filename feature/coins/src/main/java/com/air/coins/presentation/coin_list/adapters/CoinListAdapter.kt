package com.air.coins.presentation.coin_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.air.coins.R
import com.air.coins.databinding.ItemCoinInfoBinding
import com.air.coins.presentation.coin_list.model.CoinItemUi

class CoinListAdapter(
    private val onCoinClickListener: (CoinItemUi) -> Unit
) : ListAdapter<CoinItemUi, CoinListAdapter.CoinViewHolder>(CoinDiffCallback()) {

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
        private val onCoinClickListener: (CoinItemUi) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: CoinItemUi) {
            with(binding) {
                textViewName.text = coin.fromSymbol
                textViewFullName.text = coin.fullName
                textViewPrice.text = coin.currentPrice
                textViewUpdateTime.text = coin.lastUpdate
                imageViewCoinLogo.load(coin.imageUrl) {
                    placeholder(R.drawable.disk)
                }
                itemView.setOnClickListener { onCoinClickListener.invoke(coin) }
            }
        }

        fun bind(coin: CoinItemUi, payloads: MutableList<Any>) {
            if (payloads.last() !is CoinUpdates) return

            with(binding) {
                when (payloads.last() as CoinUpdates) {
                    is CoinUpdates.PriceUpdate -> {
                        textViewPrice.text = coin.currentPrice
                    }

                    is CoinUpdates.TimeUpdate -> {
                        textViewUpdateTime.text = coin.lastUpdate
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

