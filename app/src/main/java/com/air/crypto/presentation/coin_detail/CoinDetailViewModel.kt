package com.air.crypto.presentation.coin_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.air.crypto.domain.model.CoinHistory
import com.air.crypto.domain.usecase.GetCoinDetailUseCase
import com.air.crypto.domain.usecase.GetCoinHistoryUseCase
import com.air.crypto.presentation.coin_detail.mapper.CoinDetailUiMapper
import com.air.crypto.util.Failure
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    private val mapper: CoinDetailUiMapper
) : ViewModel() {
    val uiState: StateFlow<CoinDetailUiState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CoinDetailUiState())

    private val _uiEffects = MutableSharedFlow<CoinDetailUiEffects>()
    val uiEffects: SharedFlow<CoinDetailUiEffects> get() = _uiEffects.asSharedFlow()

    fun getCoinHistory(fSym: String) {
        handleLoading()

        viewModelScope.launch {
            getCoinDetailUseCase(fSym).collect { coin ->
                _uiState.update { state ->
                    val uiCoin = mapper.mapCoinItemToUi(coin)
                    state.copy(coinDetail = uiCoin)
                }
            }
        }

        viewModelScope.launch {
            getCoinHistoryUseCase(fSym).collect {
                it.either(::handleFailure, ::handleSuccess)
            }
        }
    }

    private fun handleLoading() {
        _uiState.update { state ->
            state.copy(loading = true)
        }
    }

    private fun handleSuccess(coinHistory: CoinHistory) {
        _uiState.update { state ->
            state.copy(
                loading = false,
                coinHistory = mapper.mapCoinHistoryToUi(coinHistory)
            )
        }
    }

    private fun handleFailure(failure: Failure) {
        viewModelScope.launch {
            _uiEffects.emit(CoinDetailUiEffects.FailureEffect(failure))
        }

        _uiState.update { state ->
            state.copy(
                loading = false
            )
        }
    }
}