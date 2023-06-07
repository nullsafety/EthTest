package com.lumi.ethtest.presentation.state

sealed class LoadingState {
    object Loading : LoadingState()
    data class Failed(val errorMessage: String) : LoadingState()
    object Success : LoadingState()
}