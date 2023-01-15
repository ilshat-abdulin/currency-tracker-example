package com.air.core_functional

sealed class Either<out L, out R> {
    data class Error<out L>(val error: L) : Either<L, Nothing>()
    data class Success<out R>(val value: R) : Either<Nothing, R>()

    fun either(left: (L) -> Unit, right: (R) -> Unit): Any =
        when (this) {
            is Error -> left(error)
            is Success -> right(value)
        }
}

inline fun <L, R, T> Either<L, R>.map(
    crossinline transform: (R) -> T
): Either<L, T> = when (this) {
    is Either.Success -> transform(this.value).toSuccess()
    is Either.Error -> this
}

suspend inline fun <L, R, T> Either<L, R>.suspendMap(
    crossinline transform: suspend (R) -> T
): Either<L, T> = when (this) {
    is Either.Success -> transform(this.value).toSuccess()
    is Either.Error -> this
}

suspend inline fun <L, R, T> Either<L, R>.suspendFlatMap(
    crossinline transform: suspend (R) -> Either<L, T>
): Either<L, T> =
    when (this) {
        is Either.Success -> transform(this.value)
        is Either.Error -> this.error.toError()
    }

fun <R> R.toSuccess(): Either.Success<R> = Either.Success(this)
fun <L> L.toError(): Either.Error<L> = Either.Error(this)
