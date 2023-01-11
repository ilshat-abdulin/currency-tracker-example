package com.air.core.utils

import android.util.Log
import com.air.core.network.NetworkUnavailable
import com.air.core_functional.Either
import com.air.core_functional.Failure
import com.air.core_functional.toError
import com.air.core_functional.toSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

fun Throwable.parseException(): Failure {
    return when (this) {
        is NetworkUnavailable -> Failure.NetworkUnavailable("")
        else -> Failure.UnexpectedFailure(
            message = message ?: "Exception not handled caused an Unknown failure"
        )
    }
}

suspend inline fun <T> executeRetrofitCall(
    ioDispatcher: CoroutineDispatcher,
    crossinline retrofitCall: suspend () -> T
): Either<Failure, T> {
    return withContext(ioDispatcher) {
        try {
            val res = retrofitCall().toSuccess()
            Log.d("executeRetrofitCall", res.toString())
            return@withContext res
        } catch (e: Exception) {
            return@withContext e.parseException().toError()
        }
    }
}