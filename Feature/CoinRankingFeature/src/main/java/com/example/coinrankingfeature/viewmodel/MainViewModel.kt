package com.example.coinrankingfeature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinrankingfeature.uistatemodel.CoinUIStateModel
import com.example.data.model.CoinRankingResponse
import com.example.data.model.onFailure
import com.example.data.model.onSuccess
import com.example.data.repositories.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: CoinRepository
): ViewModel() {

    private val _isShowLoading = MutableLiveData(false)
    val isShowLoading: LiveData<Boolean> = _isShowLoading

    private val _isShowToast = MutableLiveData<String>()
    val isShowToast: LiveData<String> = _isShowToast

    private val _coinList = MutableLiveData<List<CoinUIStateModel>>()
    val coinList: LiveData<List<CoinUIStateModel>> = _coinList

    private val _clickedItem = MutableLiveData<CoinUIStateModel>()
    val clickedItem: LiveData<CoinUIStateModel> = _clickedItem

    private val _isShowOnlyBookmark = MutableLiveData(false)
    val isShowOnlyBookmark: LiveData<Boolean> = _isShowOnlyBookmark

    private fun handleFailure(status: String) {
        _isShowToast.value = status
    }

    fun onSearchCoin(searchValue: String) {
        viewModelScope.launch {
            delay(1000)
            startGetAndUpdateCoinListFromApi(searchValue)
        }
    }

    fun startGetAndUpdateCoinListFromApi(searchValue: String = "") {
        viewModelScope.launch {
            _isShowLoading.value = true
            repository.getCoinListFromApi(
                offset = 0,
                limit = 100,
                searchValue = searchValue
            ).onSuccess {
                _isShowLoading.value = false
                updateCoinListOnUI(it)
                startUpdateCoinData()
            }.onFailure { failMessage ->
                delay(2000)
                stopUpdateCoinData()
                startGetAndUpdateCoinListFromApi()
                handleFailure("Fail : $failMessage")
            }
        }
    }

    private fun updateCoinListOnUI(coinRankingResponse: CoinRankingResponse) {
        _coinList.value = coinRankingResponse.data?.coins?.map {
            CoinUIStateModel.buildUIStateModel(
                model = it,
                handleOnClickEvent = { coin: CoinUIStateModel ->
                    _clickedItem.value = coin
                },
                handleOnClickBookmarkEvent = { uuid: String ->
                    viewModelScope.launch {
                        repository.bookmarkCoin(uuid)
                            .onSuccess { coinRankingResponse ->
                                updateCoinListOnUI(coinRankingResponse)
                            }
                    }
                }
            )
        }
        if (_isShowOnlyBookmark.value == true) {
            _coinList.value = _coinList.value?.filter { it.isFavorite == true }
        }
    }

    private suspend fun startUpdateCoinData() {
        repository.startUpdateCoinData(
            updateUIOnUpdateCoinDataSuccess = {
                handleFailure("update success")
                updateCoinListOnUI(it)
            },
            updateUIOnUpdateCoinDataFail = {
                handleFailure("update fail")
            }
        )
    }

    fun stopUpdateCoinData() {
        repository.stopUpdateCoinData()
    }

    fun showOnlyBookmarkedCoin() {
        _isShowOnlyBookmark.value = !_isShowOnlyBookmark.value!!
        updateCoinListOnUI(repository.getCoinList())
    }

}