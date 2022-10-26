package com.alexk.bidit.common.util

object IntegerUtils {
    fun parsePriceTypeToInt(price: String): Int =
        price.replace(",", "").toInt()

}