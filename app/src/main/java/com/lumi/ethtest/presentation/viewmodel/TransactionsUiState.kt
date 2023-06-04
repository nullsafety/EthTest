package com.lumi.ethtest.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TransactionsUiState(
    val isLoading: MutableState<Boolean> = mutableStateOf(true)
)
