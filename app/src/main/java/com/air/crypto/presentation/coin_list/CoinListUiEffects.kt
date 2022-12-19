package com.air.crypto.presentation.coin_list

sealed class CoinListUiEffects {
  data class Failure(val cause: Throwable) : CoinListUiEffects()
}
