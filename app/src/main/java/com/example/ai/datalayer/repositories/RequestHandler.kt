package com.example.ai.datalayer.repositories

import com.example.ai.datalayer.model.CoinRankingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RequestHandler {

    fun <T: Any> call(
        call: Call<T>,
        onSuccess: (T) -> Unit,
        onFailure: (status: String) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                    return
                }
                onFailure(response.code().toString())
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onFailure(t.message?:"")
            }
        })
    }
}