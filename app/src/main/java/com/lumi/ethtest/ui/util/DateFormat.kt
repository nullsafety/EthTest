package com.lumi.ethtest.ui.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.convertTimestampToDate(): String {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val date = Date(times(1000))
    return dateFormat.format(date)
}