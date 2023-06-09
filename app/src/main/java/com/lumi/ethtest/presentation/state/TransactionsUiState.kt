package com.lumi.ethtest.presentation.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.lumi.ethtest.presentation.viewmodel.TransactionUIModel

class TransactionsUiState(
    val address: MutableState<String> = mutableStateOf(""),
    val selectedTransaction: MutableState<TransactionUIModel?> = mutableStateOf(null),
    val loadingState: MutableState<LoadingState> = mutableStateOf(LoadingState.Loading),
    val transactions: MutableState<List<TransactionUIModel>> = mutableStateOf(listOf())
)

