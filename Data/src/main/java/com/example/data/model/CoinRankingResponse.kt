package com.example.data.model

import com.google.gson.annotations.SerializedName

data class CoinRankingResponse(
    @SerializedName("data")
    var data: Coins? = null,
    @SerializedName("status")
    var status: String? = null
)
