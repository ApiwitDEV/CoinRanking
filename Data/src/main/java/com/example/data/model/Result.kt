package com.example.data.model


open class Result {

    var status: String? = null

    data class Success<T: Any>(val data: T): Result()

    data class Failure(val cause: String): Result()
}