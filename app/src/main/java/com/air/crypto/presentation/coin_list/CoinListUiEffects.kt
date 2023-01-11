package com.air.crypto.presentation.coin_list

import com.air.core_functional.Failure

sealed class CoinListUiEffects {
    data class FailureEffect(val failure: Failure) : CoinListUiEffects()
}
