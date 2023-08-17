package com.example.coinrankingfeature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinrankingfeature.uistatemodel.CoinUIStateModel
import com.example.data.repositories.CoinRepository
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val repository: CoinRepository
): ViewModel() {

    private val _isShowToast = MutableLiveData<String>()
    val isShowToast: LiveData<String> = _isShowToast

    private val _coinList = MutableLiveData<List<CoinUIStateModel>>()
    val coinList: LiveData<List<CoinUIStateModel>> = _coinList

    private val _bookmarkedCoinList = MutableLiveData<List<CoinUIStateModel>>()
    val bookmarkedCoinList: LiveData<List<CoinUIStateModel>> = _bookmarkedCoinList

    private val _clickedItem = MutableLiveData<CoinUIStateModel>()
    val clickedItem: LiveData<CoinUIStateModel> = _clickedItem

    private val _isShowOnlyBookmark = MutableLiveData<Boolean>()
    val isShowOnlyBookmark: LiveData<Boolean> = _isShowOnlyBookmark

    private fun handleFailure(status: String) {
        _isShowToast.value = status
    }

    fun getCoinList(search: String = "") {
        viewModelScope.launch {
            repository.getCoinList(
                offset = 0,
                limit = 100,
                search = search,
                handleUIStateWhenSuccess = {
                    setCoinList()
                },
                handleUIStateWhenFailure = { failMessage ->
                    handleFailure("Fail : $failMessage")
                }
            )
        }
    }

    private fun setCoinList() {
        _coinList.value = repository.coinData.value?.data?.coins?.map {
            CoinUIStateModel.buildUIStateModel(
                model = it,
                handleOnClickEvent = { coin: CoinUIStateModel ->
                    _clickedItem.value = coin
                },
                handleOnClickBookmarkEvent = { uuid: String ->
                    viewModelScope.launch {
                        repository.bookmarkCoin(uuid) {
                            setCoinList()
                        }
                    }
                }
            )
        }
        _bookmarkedCoinList.value = _coinList.value?.filter { it.isFavorite == true }
        _isShowOnlyBookmark.value = false
    }

    fun showOnlyBookmarkedCoin() {
        if (_isShowOnlyBookmark.value == true) {
            _isShowOnlyBookmark.value = false
            return
        }
        _isShowOnlyBookmark.value = true
    }
}