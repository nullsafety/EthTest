package com.lumi.ethtest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumi.ethtest.data.TransactionsRepository
import com.lumi.ethtest.data.network.isError
import com.lumi.ethtest.data.network.isSuccess
import com.lumi.ethtest.presentation.state.LoadingState
import com.lumi.ethtest.presentation.state.TransactionsUiState
import com.lumi.ethtest.util.decoding.DecodedApproveFunction
import com.lumi.ethtest.util.decoding.DecodedFunction
import com.lumi.ethtest.util.decoding.DecodedTransferFunction
import com.lumi.ethtest.util.decoding.DecodingFunctionName
import com.lumi.ethtest.util.decoding.EmptyFunction
import kotlinx.coroutines.launch
import org.web3j.abi.FunctionReturnDecoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import java.lang.RuntimeException


class TransactionsViewModel(
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
                .isSuccess { response ->
                    uiState.transactions.value = response.data.result.map {
                        it.toTransactionUI(uiState.address.value)
                    }
                    uiState.loadingState.value = LoadingState.Success
                }
                .isError {
                    uiState.loadingState.value = LoadingState.Failed(it.error)
                }
        }
    }

    private fun decodeTransactionInput(rawInputData: String): DecodedFunction {
        val listOfTypes = listOf(
            object : TypeReference<Address?>() {},
            object : TypeReference<Uint256?>() {}
        )
        return when (rawInputData.substring(0, 10)) {
            DecodingFunctionName.Transfer.signature -> {
                val list = decodeInputData(
                    inputData = rawInputData,
                    methodName = DecodingFunctionName.Transfer.name,
                    outputParameters = listOfTypes
                )
                DecodedTransferFunction(
                    name = DecodingFunctionName.Transfer.name,
                    to = list?.get(0).toString(),
                    amount = list?.get(1)?.value.toString()
                )
            }

            DecodingFunctionName.Approve.signature -> {
                val list = decodeInputData(
                    inputData = rawInputData,
                    methodName = DecodingFunctionName.Approve.name,
                    outputParameters = listOfTypes
                )
                DecodedApproveFunction(
                    name = DecodingFunctionName.Approve.name,
                    spender = list?.get(0).toString(),
                    amount = list?.get(1)?.value.toString()
                )
            }

            else -> EmptyFunction()
        }
    }

    private fun decodeInputData(
        inputData: String,
        methodName: String?,
        outputParameters: List<TypeReference<*>?>?
    ): List<Type<*>?>? {
        val function = Function(
            methodName,
            emptyList(),
            outputParameters
        )
        return FunctionReturnDecoder.decode(
            inputData.substring(10),
            function.outputParameters
        )
    }

    fun onRefreshClick() = loadTransactions()

    fun onTransactionClick(transaction: TransactionUIModel) {
        val decodedFunction = try {
            decodeTransactionInput(transaction.input)
        } catch (e: RuntimeException) {
            EmptyFunction()
        }
        uiState.selectedTransaction.value = transaction.copy(decodedFunction = decodedFunction)
    }
}