package com.cartland.kitchensync.domain.model

sealed interface Result<out D, out E : AppError> {
    data class Success<out D>(
        val data: D,
    ) : Result<D, Nothing>

    data class Error<out E : AppError>(
        val error: E,
    ) : Result<Nothing, E>
}

inline fun <D, E : AppError, R> Result<D, E>.map(transform: (D) -> R): Result<R, E> =
    when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> this
    }

inline fun <D, E : AppError, F : AppError> Result<D, E>.mapError(transform: (E) -> F): Result<D, F> =
    when (this) {
        is Result.Success -> this
        is Result.Error -> Result.Error(transform(error))
    }

fun <D, E : AppError> Result<D, E>.getOrNull(): D? =
    when (this) {
        is Result.Success -> data
        is Result.Error -> null
    }

inline fun <D, E : AppError> Result<D, E>.getOrElse(defaultValue: (E) -> D): D =
    when (this) {
        is Result.Success -> data
        is Result.Error -> defaultValue(error)
    }

inline fun <D, E : AppError, R> Result<D, E>.flatMap(transform: (D) -> Result<R, E>): Result<R, E> =
    when (this) {
        is Result.Success -> transform(data)
        is Result.Error -> this
    }

inline fun <D, E : AppError> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <D, E : AppError> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    if (this is Result.Error) action(error)
    return this
}
