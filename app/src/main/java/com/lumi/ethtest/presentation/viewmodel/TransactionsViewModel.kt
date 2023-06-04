package com.lumi.ethtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.lumi.ethtest.data.TransactionsRepository
import kotlinx.coroutines.launch

class TransactionsViewModel(
    private val router: Router,
    private val repository: TransactionsRepository,
) : ViewModel() {

    val uiState: TransactionsUiState = TransactionsUiState()

    fun init(address: String?) {
        uiState.address.value = address ?: ""
        viewModelScope.launch {
            uiState.transactions.value = repository.getTransactions(uiState.address.value)
                .map {
                    it.toTransactionUI(uiState.address.value)
                }
            uiState.isLoading.value = false
        }
    }
}