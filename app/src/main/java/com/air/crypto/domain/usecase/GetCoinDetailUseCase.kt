package com.air.crypto.domain.usecase

import com.air.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinDetailUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(fromSymbol: String) = repository.getCoinDetail(fromSymbol)
}