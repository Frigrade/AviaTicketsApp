package com.example.utils

import java.text.DecimalFormat

object Helpers {

    fun formatPrice(price: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(price)
    }
}