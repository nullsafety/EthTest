package com.lumi.ethtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.lumi.ethtest.data.TransactionsRepository
import com.lumi.ethtest.data.network.isError
import com.lumi.ethtest.data.network.isSuccess
import com.lumi.ethtest.presentation.state.LoadingState
import com.lumi.ethtest.presentation.state.TransactionsUiState
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val router: Router,
    private val repository: TransactionsRepository,
) : ViewModel() {

    val uiState: TransactionsUiState = TransactionsUiState()

    fun init(address: String?) {
        uiState.address.value = address ?: ""
        loadTransactions()
    }

    private fun loadTransactions() {
        uiState.loadingState.value = LoadingState.Loading
        viewModelScope.launch {
            repository.getTransactions(uiState.address.value)
                .isSuccess { result ->
                    uiState.transactions.value = result.data.result.map {
                        it.toTransactionUI(uiState.address.value)
                    }
                }
                .isError {
                    uiState.loadingState.value = LoadingState.Failed
                }
            uiState.loadingState.value = LoadingState.Success
        }
    }

    fun onRefreshClick() = loadTransactions()

    fun onTransactionClick(transaction: TransactionUIModel) {
        uiState.selectedTransaction.value = transaction
    }
}