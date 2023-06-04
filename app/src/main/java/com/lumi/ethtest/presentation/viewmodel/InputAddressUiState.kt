package com.lumi.ethtest.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class InputAddressUiState(
    val addressInput: MutableState<String> = mutableStateOf(""),
    val loadButtonEnabled: MutableState<Boolean> = mutableStateOf(false)
)
