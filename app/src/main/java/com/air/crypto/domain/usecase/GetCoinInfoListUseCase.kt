package com.air.crypto.domain.usecase

import com.air.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getCoinInfoList()
}