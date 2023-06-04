package com.lumi.ethtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router

class TransactionsViewModel(private val router: Router) : ViewModel() {

    val uiState: TransactionsUiState = TransactionsUiState()
}