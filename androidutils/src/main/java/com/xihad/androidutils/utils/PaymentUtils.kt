package com.xihad.androidutils.utils

import java.text.DecimalFormat
import kotlin.math.ceil

object PaymentUtils {

    /** Returns the VAT amount already included in [total] at the given [taxRate] %. */
    fun getIncludingTax(total: Double, taxRate: Double): Double =
        twoDigitDouble(total * taxRate / (100 + taxRate))

    /** Returns the VAT amount to add on top of [total] at the given [taxRate] %. */
    fun getExcludingTax(total: Double, taxRate: Double): Double =
        twoDigitDouble(total * taxRate / 100)

    fun twoDigitDouble(value: Double): Double =
        "%.2f".format(value).trim().toDoubleOrNull() ?: value

    fun twoDigitString(value: Double): String = "%.2f".format(value)

    fun stringToNumber(input: String): Double = input.toDoubleOrNull() ?: 0.0

    fun String.toPriceAmount(): String = DecimalFormat("###,###,###.00").format(toDouble())
    fun Double.toPriceAmount(): String = DecimalFormat("###,###,###.00").format(this)

    /**
     * Generates up to 5 suggested cash amounts for a given [totalPrice]
     * (exact, rounded to nearest 5/10/20/50, plus ceiling), with a trailing
     * "Custom" option.
     */
    fun getCashOption(totalPrice: Double): List<CashModel> {
        if (totalPrice <= 0.0) return emptyList()

        val fmt = DecimalFormat("#.00")
        val candidates = linkedSetOf<Double>()
        candidates += twoDigitDouble(totalPrice)

        // Ceiling to nearest integer
        val ceiling = ceil(totalPrice)
        if (ceiling != totalPrice) candidates += twoDigitDouble(ceiling)

        // Round up to nearest 5, 10, 20, 50
        for (step in listOf(5, 10, 20, 50)) {
            val remainder = fmt.format(totalPrice % step).toDouble()
            if (remainder != 0.0) candidates += twoDigitDouble(totalPrice - remainder + step)
        }

        val sorted = candidates.sorted().take(5)
        return (sorted.map { amount ->
            CashModel(labelAmount = twoDigitString(amount), amount = amount)
        } + CashModel(labelAmount = "Custom", amount = 0.0))
    }
}

data class CashModel(
    var labelAmount: String = "",
    var amount: Double = 0.0
)
