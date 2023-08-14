package com.example.ai.di

import com.example.ai.datalayer.repositories.Repository
import com.example.ai.datalayer.source.local.CoinRoomDatabase
import com.example.ai.datalayer.source.remote.network.CoinRankingService
import com.example.ai.uilayer.viewmodel.DetailViewModel
import com.example.ai.uilayer.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myAppModules = module {
    single { CoinRoomDatabase.getInstance(androidContext()) }
    single { CoinRankingService() }
    single { Repository(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}