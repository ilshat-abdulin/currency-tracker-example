package com.air.crypto.presentation.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.air.crypto.domain.RequestResult
import com.air.crypto.domain.usecase.GetCoinInfoListUseCase
import com.air.crypto.domain.usecase.LoadDataUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {
    val uiState: StateFlow<CoinListUiState> get() = _uiState.asStateFlow()
    val uiEffects: SharedFlow<CoinListUiEffects> get() = _uiEffects.asSharedFlow()

    private val _uiState = MutableStateFlow(CoinListUiState())
    private val _uiEffects = MutableSharedFlow<CoinListUiEffects>()

    private var testJob: Job? = null

    init {
        loadData()
        fetchData()
    }

    fun refresh() {
        loadData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            getCoinInfoListUseCase().collect {
                _uiState.value = _uiState.value.copy(loading = false, coins = it)
            }
        }
    }

    private fun loadData() {
        cancelJob()
        handleLoading()
        testJob = viewModelScope.launch {
            ensureActive()
            loadDataUseCase().collect {
                when (it) {
                    is RequestResult.Loading -> {
                        handleLoading()
                    }
                    is RequestResult.Failure -> {
                        handleFailure(it.cause)
                    }
                    is RequestResult.Success -> {
                        handleSuccess()
                    }
                }
            }
        }
        testJob?.invokeOnCompletion {
            Log.e("periodicTaskCancel", it.toString())
        }
    }

    private fun handleSuccess() {
        _uiState.value = _uiState.value.copy(loading = false)
    }

    private fun handleLoading() {
        _uiState.value = _uiState.value.copy(loading = true)
    }

    private suspend fun handleFailure(cause: Throwable) {
        _uiState.value = _uiState.value.copy(loading = false)
        _uiEffects.emit(CoinListUiEffects.Failure(cause))
    }

    private fun cancelJob() {
        testJob?.cancel()
    }
}