package com.air.crypto.domain.usecase

import com.air.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinHistoryUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    suspend operator fun invoke(fromSymbol: String) = repository.getCoinHistory(fromSymbol)
}