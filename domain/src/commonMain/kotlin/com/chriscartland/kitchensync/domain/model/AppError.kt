package com.chriscartland.kitchensync.domain.model

interface AppError {
    val message: String
    val cause: Throwable?
        get() = null
}

sealed interface DataError : AppError {
    data class Database(
        override val message: String,
        override val cause: Throwable? = null,
    ) : DataError

    data class Network(
        override val message: String,
        override val cause: Throwable? = null,
    ) : DataError

    data class Unknown(
        override val message: String,
        override val cause: Throwable? = null,
    ) : DataError
}

sealed interface AuthError : AppError {
    data class Configuration(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AuthError

    data class SignIn(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AuthError

    data class NotAuthenticated(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AuthError

    data class Unknown(
        override val message: String,
        override val cause: Throwable? = null,
    ) : AuthError
}
