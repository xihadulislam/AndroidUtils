package com.xihad.androidutils.utils

import java.util.*

object CurrencyUtils {

    //to retrieve currency code
    fun getCurrencyCode(countryCode: String): String {
        return Currency.getInstance(Locale("", countryCode)).currencyCode
    }

    //to retrieve currency symbol
    fun getCurrencySymbol(countryCode: String): String {
        return Currency.getInstance(Locale("", countryCode)).symbol
    }

    fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


}