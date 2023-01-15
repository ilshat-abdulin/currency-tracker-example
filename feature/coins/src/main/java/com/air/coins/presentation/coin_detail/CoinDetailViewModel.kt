package com.air.coins.presentation.coin_detail

import androidx.lifecycle.*
import com.air.coins.di.FromSymbol
import com.air.coins.domain.model.CoinHistory
import com.air.coins.domain.usecase.GetCoinDetailUseCase
import com.air.coins.domain.usecase.GetCoinHistoryUseCase
import com.air.coins.presentation.coin_detail.mapper.CoinDetailUiMapper
import com.air.core_functional.Failure
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class CoinDetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    private val mapper: CoinDetailUiMapper,
    private val savedState: SavedStateHandle
) : ViewModel() {
    val uiState: StateFlow<CoinDetailUiState> get() = _uiState.asStateFlow()
    private val _uiState = MutableStateFlow(CoinDetailUiState())

    private val _uiEffects = MutableSharedFlow<CoinDetailUiEffects>()
    val uiEffects: SharedFlow<CoinDetailUiEffects> get() = _uiEffects.asSharedFlow()

    init {
        getCoinHistory()
    }

    private fun getCoinHistory() {
        val fromSymbol = savedState.get<String>(FROM_SYMBOL_KEY).orEmpty()

        viewModelScope.launch {
            getCoinDetailUseCase(fromSymbol).collect { coin ->
                _uiState.update { state ->
                    val uiCoin = mapper.mapCoinItemToUi(coin)
                    state.copy(coinDetail = uiCoin)
                }
            }
        }

        viewModelScope.launch {
            getCoinHistoryUseCase(fromSymbol)
                .onStart {
                    setLoading()
                }.collect {
                    it.either(::handleFailure, ::handleSuccess)
                }
        }
    }

    private fun setLoading() {
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

    class StateFactory @Inject constructor(
        private val getCoinDetailUseCase: Provider<GetCoinDetailUseCase>,
        private val getCoinHistoryUseCase: Provider<GetCoinHistoryUseCase>,
        private val mapper: Provider<CoinDetailUiMapper>,
        @FromSymbol private val fromSymbol: Provider<String>
    ) : AbstractSavedStateViewModelFactory() {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            handle[FROM_SYMBOL_KEY] = fromSymbol.get()

            return CoinDetailViewModel(
                getCoinDetailUseCase.get(),
                getCoinHistoryUseCase.get(),
                mapper.get(),
                handle
            ) as T
        }
    }

    companion object {
        private const val FROM_SYMBOL_KEY = "fromSymbolKey"
    }
}