package com.example.food.utils

import java.text.NumberFormat
import java.util.Locale

fun priceFormatter (price: Float): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(price)
}