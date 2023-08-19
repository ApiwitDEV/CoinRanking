package com.example.data.model

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("uuid")
    var uuId: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("color")
    var color: String? = null,
    @SerializedName("iconUrl")
    var iconUrl: String? = null,
    @SerializedName("marketCap")
    var marketCap: String? = null,
    @SerializedName("price")
    var price: Double? = null,
    @SerializedName("btcPrice")
    var btcPrice: String? = null,
    @SerializedName("listedAt")
    var listedAt: Int? = null,
    @SerializedName("change")
    var change: String? = null,
    @SerializedName("rank")
    var rank: Int? = null,
    @SerializedName("sparkline")
    var sparkLine: List<String>? = null,
    @SerializedName("coinrankingUrl")
    var coinrankingUrl: String? = null,
    @SerializedName("24hVolume")
    var _24hVolume: String? = null,
    @SerializedName("isFavorite")
    var isFavorite: Boolean? = null
)