package com.example.ai.uilayer.uistatemodel

data class CoinUIStateModel(
    val uuId: String? = null,
    val symbol: String? = null,
    val name: String? = null,
    val color: String? = null,
    val iconUrl: String? = null,
    val marketCap: String? = null,
    val price: Double? = null,
    val btcPrice: String? = null,
    val listedAt: Int? = null,
    val change: String? = null,
    val rank: Int? = null,
    val sparkLine: List<String>? = null,
    val coinrankingUrl: String? = null,
    val _24hVolume: String? = null,
    val isFavorite: Boolean? = null,
    val clickEvent: (coin: CoinUIStateModel) -> Unit,
    val clickBookmarkEvent: (uuid: String) -> Unit
)
