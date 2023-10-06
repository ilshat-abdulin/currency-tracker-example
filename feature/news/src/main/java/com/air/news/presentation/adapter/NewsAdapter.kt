package com.air.news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.air.core_ui.utils.asDpToPx
import com.air.core_ui.utils.onClickWithDebounce
import com.air.news.databinding.NewsItemBinding
import com.air.news.presentation.model.NewsUi
import com.air.news.utils.aTimeAgo

class NewsAdapter(
    private val onNewsClickListener: (NewsUi) -> Unit
) : ListAdapter<NewsUi, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

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
                newsImageView.load(item.imageUrl) {
                    transformations(RoundedCornersTransformation(8.asDpToPx().toFloat()))
                    listener(
                        onSuccess = { _, _ ->
                            imageLoadingProgress.isGone = true
                        },
                        onError = { _, _ ->
                            imageLoadingProgress.isGone = true
                        }
                    )
                }
                newsSourceNameTextView.text = item.sourceName
                newsNameTextView.text = item.title
                newsPublishedTextView.text = aTimeAgo(item.publishedOn, binding.root.context)
                itemView.onClickWithDebounce { onNewsClickListener.invoke(item) }
            }
        }
    }

    companion object {
        const val MAX_POOL_SIZE = 15
    }
}

