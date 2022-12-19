package com.air.crypto.presentation.coin_detail

sealed class CoinHistoryUiEffects {
  data class Failure(val cause: Throwable) : CoinHistoryUiEffects()
}
