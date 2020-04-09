package com.example.carousels

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repositoryModule = module {
    single { MoshiManager.moshi }
    single { Repository(ApiFactory.api) }
    viewModel { MainViewModel(get()) }
    viewModel { PlayerModel(get()) }
}


