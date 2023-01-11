package com.air.news.presentation

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.core_ui.base.BaseFragment
import com.air.news.R
import com.air.news.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(R.layout.fragment_news) {
    override val binding by viewBinding(FragmentNewsBinding::bind)
    override val viewModel: NewsViewModel by viewModels()
}