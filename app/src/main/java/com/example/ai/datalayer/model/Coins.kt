package com.example.ai.datalayer.model

import com.google.gson.annotations.SerializedName

data class Coins(
    @SerializedName("stats")
    var stats: Stats? = null,
    @SerializedName("coins")
    var coins: List<Coin>? = null
)