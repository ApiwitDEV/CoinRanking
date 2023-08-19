package com.example.data.repositories

import com.example.data.model.CoinRankingResponse
import com.example.data.model.Result
import com.example.data.model.Success
import com.example.data.model.onFailure
import com.example.data.model.onSuccess
import com.example.data.source.remote.network.RequestHandler
import com.example.data.source.local.CoinRoomDatabase
import com.example.data.source.local.entity.FavoriteCoinEntity
import com.example.data.source.remote.network.CoinRankingService
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class CoinRepository constructor(
    private val coinRankingService: CoinRankingService,
    private val favoriteCoinRoomDatabase: CoinRoomDatabase
) {

    private var coinData = CoinRankingResponse()
    private var searchValue = ""
    private var isCancel = false

    fun getCoinList(): CoinRankingResponse {
        return coinData
    }

    suspend fun getCoinListFromApi(
        offset: Int,
        limit: Int,
        searchValue: String = ""
    ): Result<CoinRankingResponse> {
        this.searchValue = searchValue
        return RequestHandler.fetchDataFromNetwork(
            endPoint = { coinRankingService.getCoinList(offset, limit, this.searchValue) }
        )
            .onSuccess { coinRankingResponse ->
                mapBookmarkedStateToCoinList(coinRankingResponse, getBookmarkedCoin())
            }
            .onFailure {

            }
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
        coinData = coinRankingResponse
    }

    private suspend fun getBookmarkedCoin(): List<FavoriteCoinEntity> {
        return favoriteCoinRoomDatabase.coinDao().getFavoriteCoin()
    }

    suspend fun bookmarkCoin(
        uuid: String
    ): Success<CoinRankingResponse> {
        coroutineScope {
            coinData.data?.coins?.forEach {
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
            mapBookmarkedStateToCoinList(coinData, getBookmarkedCoin())
        }
        return Success(coinData)
    }

    suspend fun startUpdateCoinData(
        updateUIOnUpdateCoinDataSuccess: (CoinRankingResponse) -> Unit = {},
        updateUIOnUpdateCoinDataFail: (String) -> Unit = {}
    ) {
        isCancel = false
        updateCoinData(
            updateUIOnUpdateCoinDataSuccess,
            updateUIOnUpdateCoinDataFail
        )
    }

    fun stopUpdateCoinData() {
        isCancel = true
    }

    private suspend fun updateCoinData(
        updateUIOnUpdateCoinDataSuccess: (CoinRankingResponse) -> Unit = {},
        updateUIOnUpdateCoinDataFail: (String) -> Unit = {}
    ) {
        delay(10000)
        if (!isCancel) {
            getCoinListFromApi(0, 100, searchValue)
                .onSuccess {
                    updateUIOnUpdateCoinDataSuccess(it)
                    updateCoinData(
                        updateUIOnUpdateCoinDataSuccess,
                        updateUIOnUpdateCoinDataFail
                    )
                }
                .onFailure {
                    updateUIOnUpdateCoinDataFail(it)
                    updateCoinData(
                        updateUIOnUpdateCoinDataSuccess,
                        updateUIOnUpdateCoinDataFail
                    )
                }
        }
    }
}