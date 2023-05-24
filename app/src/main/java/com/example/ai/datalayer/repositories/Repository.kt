package com.example.ai.datalayer.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ai.datalayer.model.CoinRankingResponse
import com.example.ai.datalayer.source.local.CoinRoomDatabase
import com.example.ai.datalayer.source.local.entity.FavoriteCoinEntity
import com.example.ai.datalayer.source.remote.CoinRankingService
import kotlin.concurrent.fixedRateTimer

class Repository constructor(
    private val coinRankingService: CoinRankingService,
    private val favoriteCoinRoomDatabase: CoinRoomDatabase
) {

    private val apiKey = "coinranking49246176de07b17763f685360c399d6097bc8d20d6a1aa09"

    private val _coinData = MutableLiveData<CoinRankingResponse>()
    val coinData: LiveData<CoinRankingResponse> = _coinData

    fun getCoinList(
        offset: Int,
        limit: Int,
        search: String = "",
        handleUIStateWhenSuccess: () -> Unit,
        handleUIStateWhenFailure: (String) -> Unit
    ) {
        RequestHandler.call(
            call = coinRankingService.getCoinList(apiKey, offset, limit, search),
            onSuccess = { coinRankingResponse: CoinRankingResponse ->
                mapBookmarkedStateToCoinList(
                    coinRankingResponse,
                    getBookmarkedCoin()
                )
                handleUIStateWhenSuccess()
            },
            onFailure = { failMessage ->
                handleUIStateWhenFailure(failMessage)
            }
        )
    }

    private fun mapBookmarkedStateToCoinList(
        coinRankingResponse: CoinRankingResponse,
        bookmarkedCoin: List<FavoriteCoinEntity>
    ) {
        if (bookmarkedCoin.isEmpty()) {
            coinRankingResponse.data?.coins?.map { it.isFavorite = false }
        }
        for (coin in coinRankingResponse.data?.coins!!) {
            for (favoriteCoin in bookmarkedCoin) {
                if (coin.uuId == favoriteCoin.uuid) {
                    coin.isFavorite = true
                    break
                } else {
                    coin.isFavorite = false
                }
            }
        }
        _coinData.value = coinRankingResponse
    }

    private fun getBookmarkedCoin(): List<FavoriteCoinEntity> {
        return favoriteCoinRoomDatabase.coinDao().getFavoriteCoin()
    }

    fun bookmarkCoin(
        uuid: String,
        updateCoinListUIState: () -> Unit
    ) {
        coinData.value?.data?.coins?.forEach {
            if (it.uuId == uuid) {
                val favoriteCoinEntity = FavoriteCoinEntity(
                    uuid = it.uuId!!,
                    name = it.name,
                    symbol = it.name,
                    coinrankingUrl = it.coinrankingUrl
                )
                if (it.isFavorite == true) {
                    favoriteCoinRoomDatabase.coinDao().delete(favoriteCoinEntity)
                } else {
                    favoriteCoinRoomDatabase.coinDao().insert(favoriteCoinEntity)
                }
            }
        }
        mapBookmarkedStateToCoinList(coinData.value!!, getBookmarkedCoin())
        updateCoinListUIState()
    }
}