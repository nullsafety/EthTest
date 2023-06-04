package com.lumi.ethtest.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.lumi.ethtest.presentation.fragment.InputAddressFragment
import com.lumi.ethtest.presentation.viewmodel.InputAddressViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Cicerone.create(Router()) }
    single { get<Cicerone<Router>>().getNavigatorHolder() }
    single { get<Cicerone<Router>>().router }
}

val inputAddressFragmentModule = module {
    scope<InputAddressFragment> {
        viewModel { InputAddressViewModel(get()) }
    }
}