package com.lumi.ethtest.ui.util

fun String.weiToEther() = toDouble().times(0.000000000000000001).toString()