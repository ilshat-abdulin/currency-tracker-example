package com.air.news.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.air.news.presentation.model.NewsUi

class NewsDiffCallback : DiffUtil.ItemCallback<NewsUi>() {
    override fun areItemsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
        return oldItem == newItem
    }
}