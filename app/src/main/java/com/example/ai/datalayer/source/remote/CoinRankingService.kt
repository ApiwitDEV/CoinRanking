package com.example.ai.datalayer.source.remote

import com.example.ai.datalayer.model.CoinRankingResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CoinRankingService: ApiInterface {

    private var retrofitService: ApiInterface
    private val BASE_URL = "https://api.coinranking.com/v2/"

    init {
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(ApiInterface::class.java)
    }

    override fun getCoinList(apiKey: String, offset: Int, limit: Int, search: String): Call<CoinRankingResponse> {
        return retrofitService.getCoinList(apiKey, offset, limit, search)
    }
}