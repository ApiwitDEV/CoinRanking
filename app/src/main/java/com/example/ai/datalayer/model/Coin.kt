package com.example.ai.datalayer.model

import com.example.ai.uilayer.uistatemodel.CoinUIStateModel
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
) {
    fun convertToCoinUIStateModel(
        handleOnClickEvent:(coin: CoinUIStateModel) -> Unit = {},
        handleOnClickBookmarkEvent:(uuid: String) -> Unit = {}) = CoinUIStateModel(
        uuId = this@Coin.uuId,
        symbol = this@Coin.symbol,
        name= this@Coin.name,
        color = this@Coin.color,
        iconUrl = this@Coin.iconUrl,
        marketCap = this@Coin.marketCap,
        price = this@Coin.price,
        btcPrice = this@Coin.btcPrice,
        listedAt = this@Coin.listedAt,
        change = this@Coin.change,
        rank = this@Coin.rank,
        sparkLine = this@Coin.sparkLine,
        coinrankingUrl = this@Coin.coinrankingUrl,
        _24hVolume = this@Coin._24hVolume,
        isFavorite = this@Coin.isFavorite,
        clickEvent = handleOnClickEvent,
        clickBookmarkEvent = handleOnClickBookmarkEvent
    )
}