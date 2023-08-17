package com.example.data.source.remote.network

import com.example.data.model.CoinRankingResponse
import com.example.data.source.remote.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinRankingService {

    private var retrofitService: ApiInterface
    private val BASE_URL = "https://api.coinranking.com/v2/"
    private val apiKey = "coinranking49246176de07b17763f685360c399d6097bc8d20d6a1aa09"

    init {
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(ApiInterface::class.java)
    }

    suspend fun getCoinList(offset: Int, limit: Int, search: String): CoinRankingResponse {
        return retrofitService.getCoinList(apiKey, offset, limit, search)
    }
}