package com.lumi.ethtest.presentation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.lumi.ethtest.presentation.fragment.InputAddressFragment
import com.lumi.ethtest.presentation.fragment.TransactionsFragment

object Screens {
    fun inputAddressScreen() = FragmentScreen {
        InputAddressFragment()
    }
    fun transactionsScreen(address: String) = FragmentScreen {
        TransactionsFragment.getInstance(address)
    }
}