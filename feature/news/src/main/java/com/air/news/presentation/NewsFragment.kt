package com.air.news.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.air.core.di.CoreComponentProvider
import com.air.core_functional.Failure
import com.air.core_ui.base.BaseFragment
import com.air.news.R
import com.air.news.databinding.FragmentNewsBinding
import com.air.news.di.DaggerNewsComponent
import com.air.news.di.scopedComponent
import com.air.news.presentation.adapter.NewsAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(R.layout.fragment_news) {
    private val coreComponent by lazy {
        (requireActivity().application as CoreComponentProvider).coreComponent
    }

    private val newsComponent by scopedComponent {
        DaggerNewsComponent.factory().create(coreComponent)
    }

    @Inject
    lateinit var newsViewModelFactory: dagger.Lazy<NewsViewModel.Factory>

    private val newsAdapter by lazy {
        NewsAdapter {
            openWebPage(it.url)
        }
    }

    override val binding by viewBinding(FragmentNewsBinding::bind)
    override val viewModel: NewsViewModel by viewModels {
        newsViewModelFactory.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        newsComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeUiState()
        observeUiEffects()
        setUiEventListeners()
    }

    private fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent)
    }

    private fun setUiEventListeners() {
        binding.root.setOnRefreshListener {
            binding.root.isRefreshing = false
            viewModel.getNews()
        }
    }

    private fun setRecyclerView() {
        with(binding.newsRecyclerView) {
            adapter = newsAdapter
            recycledViewPool.setMaxRecycledViews(0, NewsAdapter.MAX_POOL_SIZE)
        }
    }

    private fun observeUiState() {
        viewModel.getNews()
        viewModel.uiState
            .onEach { updateUi(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeUiEffects() {
        viewModel.uiEffects
            .onEach {
                when (it) {
                    is NewsUiEffects.FailureEffect -> handleError(it.failure)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun updateUi(uiState: NewsUiState) {
        val (loading, news) = uiState

        with(binding) {
            if (loading) {
                newsLoading.isVisible = true
                newsLoading.setText(getString(R.string.news_loading))
            } else if (news.isEmpty()) {
                newsLoading.isVisible = false
                newsEmptyTextView.isVisible = true
            } else {
                newsLoading.isVisible = false
                newsEmptyTextView.isVisible = false
            }
        }

        newsAdapter.submitList(news) {
            binding.newsRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun handleError(failure: Failure) {
        val message = when (failure) {
            is Failure.NetworkUnavailable -> getString(R.string.network_error)
            else -> getString(R.string.error_message)
        }
        showSnackbar(message)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}