package com.air.crypto.domain.usecase

import com.air.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}