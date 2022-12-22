package com.air.crypto.presentation.coin_detail

import androidx.lifecycle.ViewModel
import com.air.crypto.domain.usecase.GetCoinHistoryUseCase
import com.air.crypto.domain.usecase.GetCoinInfoUseCase
import com.air.crypto.presentation.coin_detail.mapper.CoinHistoryUiMapper
import com.air.crypto.presentation.coin_list.CoinListUiEffects
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CoinDetailViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase,
    private val historyMapper: CoinHistoryUiMapper
) : ViewModel() {
    val historyUiState: StateFlow<CoinHistoryUiState> get() = _historyUiState.asStateFlow()
    val historyUiEffects: SharedFlow<CoinListUiEffects> get() = _historyUiEffects.asSharedFlow()

    private val _historyUiState = MutableStateFlow(CoinHistoryUiState())
    private val _historyUiEffects = MutableSharedFlow<CoinListUiEffects>()

    suspend fun getCoinHistory(fSym: String) {
        getCoinHistoryUseCase(fSym).collect { coinHistory ->
            _historyUiState.value = _historyUiState.value.copy(
                loading = false,
                coinHistory = historyMapper.mapCoinHistoryToUi(coinHistory)
            )
        }
    }


    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)
}