package com.air.news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.air.core_ui.extensions.loadImage
import com.air.news.databinding.NewsItemBinding
import com.air.news.presentation.model.NewsUi
import com.air.news.utils.aTimeAgo

class NewsAdapter(private val onNewsClickListener: (NewsUi) -> Unit) :
    ListAdapter<NewsUi, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = NewsViewHolder(
        binding = NewsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        onNewsClickListener = onNewsClickListener
    )

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(
        private val binding: NewsItemBinding,
        private val onNewsClickListener: (NewsUi) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewsUi) {
            with(binding) {
                newsImageView.loadImage(item.imageUrl)
                newsSourceNameTextView.text = item.sourceName
                newsNameTextView.text = item.title
                newsPublishedTextView.text = aTimeAgo(item.publishedOn, binding.root.context)
                itemView.setOnClickListener { onNewsClickListener.invoke(item) }
            }
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}

