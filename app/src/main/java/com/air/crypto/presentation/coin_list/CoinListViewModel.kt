package com.air.crypto.presentation.coin_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.air.crypto.domain.usecase.GetCoinListUseCase
import com.air.crypto.domain.usecase.LoadDataUseCase
import com.air.crypto.presentation.coin_list.mapper.CoinListUiMapper
import com.air.core_functional.Failure
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val mapper: CoinListUiMapper
) : ViewModel() {
    val uiState: StateFlow<CoinListUiState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CoinListUiState())

    private val _uiEffects = MutableSharedFlow<CoinListUiEffects>()
    val uiEffects: SharedFlow<CoinListUiEffects> get() = _uiEffects.asSharedFlow()

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
            getCoinListUseCase().collect {
                val uiCoins = it.map { mapper.mapCoinItemToUi(it) }
                _uiState.update { state ->
                    state.copy(loading = false, coins = uiCoins)
                }
            }
        }
    }

    private fun loadData() {
        cancelJob()
        handleLoading()

        testJob = viewModelScope.launch {
            ensureActive()
            loadDataUseCase().collect {
                it.either(::handleFailure, ::handleSuccess)
            }
        }

        testJob?.invokeOnCompletion {
            Log.e("CoinListViewModel", it.toString())
        }
    }

    private fun handleSuccess(unit: Unit) {
        _uiState.update { state ->
            state.copy(loading = false)
        }
    }

    private fun handleLoading() {
        _uiState.update { state ->
            state.copy(loading = true)
        }
    }

    private fun handleFailure(failure: Failure) {
        viewModelScope.launch {
            _uiEffects.emit(CoinListUiEffects.FailureEffect(failure))
        }

        _uiState.update { state ->
            state.copy(
                loading = false
            )
        }
    }

    private fun cancelJob() {
        testJob?.cancel()
    }
}