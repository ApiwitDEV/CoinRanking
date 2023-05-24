package com.example.ai.uilayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ai.datalayer.repositories.Repository
import com.example.ai.uilayer.uistatemodel.CoinUIStateModel

class DetailViewModel constructor(
    private val repository: Repository
): ViewModel() {

    private val _coinDetail = MutableLiveData<CoinUIStateModel>()
    val coinDetail: LiveData<CoinUIStateModel> = _coinDetail

    fun getIndividualCoinDetail(uuid: String) {
        _coinDetail.value = repository.coinData.value?.data?.coins
            ?.filter { it.uuId == uuid }
            ?.map { it.convertToCoinUIStateModel() }
            ?.first()
    }
}