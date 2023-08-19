package com.example.data.source.remote.network

import com.example.data.model.Failure
import com.example.data.model.Result
import com.example.data.model.Success
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
        endPoint: suspend () -> T
//        onSuccess: suspend (T) -> Unit,
//        onFailure: suspend (status: String) -> Unit
    ): Result<T> {
        return try {
            withTimeout(5000) {
                //onSuccess(endPoint)
                Success(endPoint())
            }
        } catch (error: Exception) {
            //onFailure(error.message.toString())
            Failure(error.message.toString())
        }
    }
}