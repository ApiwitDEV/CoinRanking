package com.example.coinrankingfeature.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinrankingfeature.uistatemodel.CoinUIStateModel
import com.example.data.repositories.CoinRepository

class DetailViewModel constructor(
    private val repository: CoinRepository
): ViewModel() {

    private val _coinDetail = MutableLiveData<CoinUIStateModel>()
    val coinDetail: LiveData<CoinUIStateModel> = _coinDetail

//    fun getIndividualCoinDetail(uuid: String) {
//        _coinDetail.value = repository.coinData.value?.data?.coins
//            ?.filter { it.uuId == uuid }
//            ?.map { CoinUIStateModel.buildUIStateModel(it) }
//            ?.first()
//    }
}