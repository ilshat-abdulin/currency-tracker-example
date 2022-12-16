package com.air.crypto.presentation.coin_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.air.crypto.R
import com.air.crypto.databinding.ItemCoinInfoBinding
import com.air.crypto.domain.model.CoinInfo
import com.air.crypto.loadImage

class CoinListAdapter(private val onCoinClickListener: (CoinInfo) -> Unit) :
    ListAdapter<CoinInfo, CoinListAdapter.CoinViewHolder>(CoinDiffCallback()) {

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

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CoinViewHolder(
        private val binding: ItemCoinInfoBinding,
        private val onCoinClickListener: (CoinInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coinInfo: CoinInfo) {
            with(binding) {
                textViewName.text = coinInfo.fromSymbol
                textViewFullName.text = coinInfo.fullName
                textViewPrice.text = "$${coinInfo.currentPrice}"
                textViewUpdateTime.text = coinInfo.lastUpdate
                imageViewCoinLogo.loadImage(coinInfo.imageUrl)
                itemView.setOnClickListener { onCoinClickListener.invoke(coinInfo) }
            }
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}