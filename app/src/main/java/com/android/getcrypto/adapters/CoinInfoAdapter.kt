package com.android.getcrypto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.getcrypto.R
import com.android.getcrypto.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]

        val symbolsTemplate = context.resources.getString(R.string.symbols_template)
        val lastUpdateTemplate = context.resources.getString(R.string.last_time_template)

        holder.textViewIndex.text = (position + 1).toString()
        holder.textViewSymbols.text = String.format(symbolsTemplate, coin.fromSymbol, coin.toSymbol)
        holder.textViewPrice.text = coin.price.toString()
        holder.textViewTimeUpdate.text = String.format(lastUpdateTemplate, coin.getFormattedTime())
        Picasso.get().load(coin.getFullImageUrl()).into(holder.imageViewCoinLogo)

        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
    }

    override fun getItemCount(): Int = coinInfoList.size


    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewIndex: TextView = itemView.findViewById(R.id.textViewIndex)
        val textViewSymbols: TextView = itemView.findViewById(R.id.textViewSymbols)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        val textViewTimeUpdate: TextView = itemView.findViewById(R.id.textViewTimeUpdate)
        val imageViewCoinLogo: ImageView = itemView.findViewById(R.id.imageViewCoinLogo)


    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }

}