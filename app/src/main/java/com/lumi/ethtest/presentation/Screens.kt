package com.lumi.ethtest.presentation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.lumi.ethtest.presentation.fragment.MainFragment

object Screens {
    fun mainScreen() = FragmentScreen {
        MainFragment()
    }
}