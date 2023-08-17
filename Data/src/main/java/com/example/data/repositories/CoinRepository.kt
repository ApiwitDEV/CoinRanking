package com.example.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.model.CoinRankingResponse
import com.example.data.source.remote.network.RequestHandler
import com.example.data.source.local.CoinRoomDatabase
import com.example.data.source.local.entity.FavoriteCoinEntity
import com.example.data.source.remote.network.CoinRankingService

class CoinRepository constructor(
    private val coinRankingService: CoinRankingService,
    private val favoriteCoinRoomDatabase: CoinRoomDatabase
) {

    private val _coinData = MutableLiveData<CoinRankingResponse>()
    val coinData: LiveData<CoinRankingResponse> = _coinData

    suspend fun getCoinList(
        offset: Int,
        limit: Int,
        search: String = "",
        handleUIStateWhenSuccess: () -> Unit,
        handleUIStateWhenFailure: (String) -> Unit
    ) {
        RequestHandler.fetchDataFromNetwork(
            endPoint = coinRankingService.getCoinList(offset, limit, search),
            onSuccess = { coinRankingResponse ->
                mapBookmarkedStateToCoinList(coinRankingResponse, getBookmarkedCoin())
                handleUIStateWhenSuccess()
            },
            onFailure = handleUIStateWhenFailure
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

    private suspend fun getBookmarkedCoin(): List<FavoriteCoinEntity> {
        return favoriteCoinRoomDatabase.coinDao().getFavoriteCoin()
    }

    suspend fun bookmarkCoin(
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