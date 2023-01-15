package com.air.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.air.core_functional.Failure
import com.air.news.domain.model.Article
import com.air.news.domain.usecase.GetNewsUseCase
import com.air.news.presentation.mapper.NewsUiMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val newsUiMapper: NewsUiMapper
) : ViewModel() {
    val uiState: StateFlow<NewsUiState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(NewsUiState())

    private val _uiEffects = MutableSharedFlow<NewsUiEffects>()
    val uiEffects: SharedFlow<NewsUiEffects> get() = _uiEffects.asSharedFlow()

    fun getNews() {
        setLoading()

        viewModelScope.launch {
            getNewsUseCase.invoke()
                .either(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(news: List<Article>) {
        _uiState.update { state ->
            state.copy(loading = false, news = news.map {
                newsUiMapper.mapArticleToUi(it)
            })
        }
    }

    private fun setLoading() {
        _uiState.update { state ->
            state.copy(loading = true)
        }
    }

    private fun handleFailure(failure: Failure) {
        viewModelScope.launch {
            _uiEffects.emit(NewsUiEffects.FailureEffect(failure))
        }

        _uiState.update { state ->
            state.copy(
                loading = false
            )
        }
    }

    class Factory @Inject constructor(
        private val getNewsUseCase: Provider<GetNewsUseCase>,
        private val newsUiMapper: Provider<NewsUiMapper>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewsViewModel(
                getNewsUseCase.get(),
                newsUiMapper.get()
            ) as T
        }
    }
}