package com.air.crypto.domain

sealed class RequestResult {
    object Loading : RequestResult()
    object Success : RequestResult()
    data class Failure(val cause: Throwable) : RequestResult()
}


