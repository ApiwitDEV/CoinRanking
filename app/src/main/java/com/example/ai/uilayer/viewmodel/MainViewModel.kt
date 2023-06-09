package com.example.ai.uilayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ai.datalayer.repositories.Repository
import com.example.ai.uilayer.uistatemodel.CoinUIStateModel

class MainViewModel constructor(
    private val repository: Repository
): ViewModel() {

    private val _showToastSuccess = MutableLiveData<Unit>()
    val showToastSuccess: LiveData<Unit> = _showToastSuccess

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
        repository.getCoinList(
            offset = 0,
            limit = 100,
            search = search,
            handleUIStateWhenSuccess = {
//                _showToastSuccess.value = Unit
                setCoinList()
            },
            handleUIStateWhenFailure = { failMessage ->
                handleFailure("Fail : $failMessage")
            }
        )
    }

    private fun setCoinList() {
        _coinList.value = repository.coinData.value?.data?.coins?.map {
            it.convertToCoinUIStateModel(
                handleOnClickEvent = { coin: CoinUIStateModel ->
                    _clickedItem.value = coin
                },
                handleOnClickBookmarkEvent = { uuid: String ->
                    repository.bookmarkCoin(uuid) {
                        setCoinList()
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