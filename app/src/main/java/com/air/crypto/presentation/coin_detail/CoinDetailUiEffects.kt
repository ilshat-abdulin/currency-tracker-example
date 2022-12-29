package com.air.crypto.presentation.coin_detail

import com.air.crypto.util.Failure

sealed class CoinDetailUiEffects {
    data class FailureEffect(val failure: Failure) : CoinDetailUiEffects()
}
