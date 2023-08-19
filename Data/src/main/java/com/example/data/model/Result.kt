package com.example.data.model


open class Result<T: Any> {

    var status: String? = null
}

data class Success<T: Any>(val data: T): Result<T>()

data class Failure<T: Any>(val cause: String): Result<T>()

suspend fun <T: Any> Result<T>.onSuccess(action: suspend (T) -> Unit): Result<T> {
    if (this is Success<T>) {
        action(this.data)
    }
    return this
}

suspend fun <T: Any> Result<T>.onFailure(action: suspend (String) -> Unit): Result<T> {
    if (this is Failure<T>) {
        action(this.cause)
    }
    return this
}