package com.air.crypto.presentation.coin_list

import com.air.crypto.util.Failure

sealed class CoinListUiEffects {
    data class FailureEffect(val failure: Failure) : CoinListUiEffects()
}
