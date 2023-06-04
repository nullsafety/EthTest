package com.lumi.ethtest.presentation.state

sealed class LoadingState {
    object Loading : LoadingState()
    object Failed : LoadingState()
    object Success : LoadingState()
}