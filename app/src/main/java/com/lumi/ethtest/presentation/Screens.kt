package com.lumi.ethtest.presentation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.lumi.ethtest.presentation.fragment.InputAddressFragment

object Screens {
    fun inputAddressScreen() = FragmentScreen {
        InputAddressFragment()
    }
}