package com.air.coins.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last

fun <T> Flow<T>.repeatWithDelay(delay: Long): Flow<T> {
    return flow {
        do {
            emit(this@repeatWithDelay.last())
            kotlinx.coroutines.delay(delay)
        } while (true)
    }
}