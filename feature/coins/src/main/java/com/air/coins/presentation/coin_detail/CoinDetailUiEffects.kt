package com.air.coins.presentation.coin_detail

import com.air.core_functional.Failure

sealed class CoinDetailUiEffects {
    data class FailureEffect(val failure: Failure) : CoinDetailUiEffects()
}
