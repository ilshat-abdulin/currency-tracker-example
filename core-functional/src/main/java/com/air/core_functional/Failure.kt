package com.air.core_functional

sealed class Failure {
    data class NetworkUnavailable(val message: String?) : Failure()
    data class UnexpectedFailure(val message: String?) : Failure()
}
