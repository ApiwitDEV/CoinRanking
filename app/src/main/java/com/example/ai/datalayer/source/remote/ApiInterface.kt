package com.example.ai.datalayer.source.remote

import com.example.ai.datalayer.model.CoinRankingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {

    @GET("coins")
    fun getCoinList(
        @Header("Authorization") apiKey: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String
    ): Call<CoinRankingResponse>
}