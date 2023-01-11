package com.air.coins.domain.usecase

import com.air.coins.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getCoinList()
}