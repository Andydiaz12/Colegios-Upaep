package com.upaep.colegios.viewmodel.base

import java.text.NumberFormat
import java.util.Locale

object GeneralMethods {
    fun currencyParse(string: String) : String = NumberFormat.getCurrencyInstance(Locale.US).format(string.toFloat())
}