package com.lumi.ethtest.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.lumi.ethtest.ui.theme.EthTestTheme

fun Fragment.setAppThemeContent(content: @Composable () -> Unit): ComposeView {
    return ComposeView(requireContext()).apply {
        setContent {
            EthTestTheme {
                content.invoke()
            }
        }
    }
}