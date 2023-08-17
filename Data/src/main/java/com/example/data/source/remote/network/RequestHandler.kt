package com.example.data.source.remote.network

import kotlinx.coroutines.withTimeout

object RequestHandler {

//    fun <T: Any> call(
//        call: Call<T>,
//        onSuccess: (T) -> Unit,
//        onFailure: (status: String) -> Unit
//    ) {
//        call.enqueue(object : Callback<T> {
//            override fun onResponse(
//                call: Call<T>,
//                response: Response<T>
//            ) {
//                if (response.isSuccessful) {
//                    onSuccess(response.body()!!)
//                    return
//                }
//                onFailure(response.code().toString())
//            }
//
//            override fun onFailure(call: Call<T>, t: Throwable) {
//                onFailure(t.message?:"")
//            }
//        })
//    }

    suspend fun <T: Any> fetchDataFromNetwork(
        endPoint: T,
        onSuccess: suspend (T) -> Unit,
        onFailure: suspend (status: String) -> Unit
    ) {
        try {
            withTimeout(5000) {
                onSuccess(endPoint)
            }
        } catch (error: Exception) {
            onFailure(error.message.toString())
        }
    }
}