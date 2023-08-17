package com.example.coinrankingfeature.di

import com.example.coinrankingfeature.viewmodel.DetailViewModel
import com.example.coinrankingfeature.viewmodel.MainViewModel
import com.example.data.repositories.CoinRepository
import com.example.data.source.local.CoinRoomDatabase
import com.example.data.source.remote.network.CoinRankingService
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModules = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val dataModules = module {
    single { CoinRoomDatabase.getInstance(androidContext()) }
    single { CoinRankingService() }
    single { CoinRepository(get(), get()) }
}

val allModules = dataModules + featureModules