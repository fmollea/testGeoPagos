package com.mollea.testgeopagos.extensions

import java.text.NumberFormat
import java.util.*

fun formatCurrency(amount: String = "0"): String {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance("AR")
    return format.format(amount)
}