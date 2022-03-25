package com.air.crypto.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.air.crypto.R
import com.air.crypto.domain.model.CoinInfo
import com.bumptech.glide.Glide

class CoinListAdapter : ListAdapter<CoinInfo, CoinViewHolder>(CoinDiffCallback()) {

    lateinit var context: Context
    var onCoinClickListener: ((CoinInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        context = parent.context
        return CoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        val coin = getItem(position)

        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_time_template)

        holder.textViewIndex.text = (position + 1).toString()
        holder.textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
        holder.textViewPrice.text = coin.price.toString()
        holder.textViewTimeUpdate.text = String.format(lastUpdateTemplate, coin.lastUpdate)
        Glide.with(context).load(coin.imageUrl).into(holder.imageViewCoinLogo)

        holder.itemView.setOnClickListener {
            onCoinClickListener?.invoke(coin)
        }
    }

    override fun onViewRecycled(holder: CoinViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}