package com.example.coinrankingfeature.uistatemodel

import com.example.data.model.Coin

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
    val clickEvent: (coin: CoinUIStateModel) -> Unit = {},
    val clickBookmarkEvent: (uuid: String) -> Unit = {}
) {
    companion object {
        fun buildUIStateModel(
            model: Coin,
            handleOnClickEvent: (coin: CoinUIStateModel) -> Unit = {},
            handleOnClickBookmarkEvent: (uuid: String) -> Unit = {}
        ): CoinUIStateModel {
            return CoinUIStateModel(
                uuId = model.uuId,
                symbol = model.symbol,
                name = model.name,
                color = model.color,
                iconUrl = model.iconUrl,
                marketCap = model.marketCap,
                price = model.price,
                btcPrice = model.btcPrice,
                listedAt = model.listedAt,
                change = model.change,
                rank = model.rank,
                sparkLine = model.sparkLine,
                coinrankingUrl = model.coinrankingUrl,
                _24hVolume = model._24hVolume,
                isFavorite = model.isFavorite,
                clickEvent = handleOnClickEvent,
                clickBookmarkEvent = handleOnClickBookmarkEvent
            )
        }
    }
}
