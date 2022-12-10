package com.air.crypto.domain

import javax.inject.Inject

class GetCoinHistoryUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(fromSymbol: String) = repository.getCoinHistory(fromSymbol)
}