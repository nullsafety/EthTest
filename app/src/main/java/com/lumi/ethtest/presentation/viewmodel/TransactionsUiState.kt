package com.lumi.ethtest.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TransactionsUiState(
    val address: MutableState<String> = mutableStateOf(""),
    val isLoading: MutableState<Boolean> = mutableStateOf(true),
    val transactions: MutableState<List<TransactionUIModel>> = mutableStateOf(listOf())
)
