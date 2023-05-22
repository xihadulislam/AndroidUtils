package com.xihad.androidutils.utils

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.util.*
import kotlin.math.ceil

object PaymentUtils {


    fun getIncludingTax(total: Double, tax: Double): Double {
        // VAT amount = Value inclusive of tax X tax rate ÷ (100 + tax rate)
        return twoDigitDouble(total * tax / (100 + tax))
    }

    fun getExcludingTax(total: Double, tax: Double): Double {
        return twoDigitDouble(total * tax / 100)
    }


    @SuppressLint("DefaultLocale")
    fun twoDigitDouble(value: Double): Double {
        var newVal = 0.00
        try {
            newVal = String.format(" %.2f", value).toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return newVal
    }

    @SuppressLint("DefaultLocale")
    fun twoDigitString(value: Double): String {
        var newVal = ""
        newVal = try {
            String.format(" %.2f", value)
        } catch (e: Exception) {
            e.printStackTrace()
            value.toString()
        }
        return newVal
    }

    fun stringToNumber(inputNumber: String): Double {
        var outputNumber = 0.00
        try {
            outputNumber = inputNumber.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return outputNumber
    }

    fun getCashOption(totalPrice: Double): List<CashModel> {

        val cashList: MutableList<CashModel> = ArrayList()
        val decimalFormat = DecimalFormat("#.00")
        val currencyArray: MutableList<Double> = ArrayList()
        if (totalPrice > 0.00) {
            currencyArray.add(twoDigitDouble(totalPrice))
            var gtm5 = totalPrice % 5
            gtm5 = decimalFormat.format(gtm5).toDouble()
            if (gtm5 != 0.0) {
                currencyArray.add(twoDigitDouble(totalPrice - gtm5 + 5))
            }
            var gtm10 = totalPrice % 10
            gtm10 = decimalFormat.format(gtm10).toDouble()
            if (gtm10 != 0.0) {
                currencyArray.add(twoDigitDouble(totalPrice - gtm10 + 10))
            }
            var gtm20 = totalPrice % 20
            if (gtm20 != 0.0) {
                gtm20 = decimalFormat.format(gtm20).toDouble()
                currencyArray.add(twoDigitDouble(totalPrice - gtm20 + 20))
            }
            var gtm50 = totalPrice % 50
            if (gtm50 != 0.0) {
                gtm50 = decimalFormat.format(gtm50).toDouble()
                currencyArray.add(twoDigitDouble(totalPrice - gtm50 + 50))
            }
            if (currencyArray.isNotEmpty() && currencyArray[0] % 1 != 0.0) {
                currencyArray.add(1, twoDigitDouble(ceil(currencyArray[0])))
            }
            val hs: Set<Double> = HashSet(currencyArray)
            currencyArray.clear()
            currencyArray.addAll(hs)
            currencyArray.sort()
            if (currencyArray.size == 6) currencyArray.removeAt(5)
            currencyArray.add(-5.5)
            for (i in currencyArray.indices) {
                val cashModel = CashModel()
                if (i == 0) {
                    if (currencyArray[i] == 5.501) {
                        cashModel.amount = 0.0
                        cashModel.labelAmount = "Custom"
                    } else {
                        cashModel.labelAmount = twoDigitString(currencyArray[i])
                        cashModel.amount = twoDigitDouble(currencyArray[i])
                    }
                    cashList.add(cashModel)
                }
                if (i == 1) {
                    if (currencyArray[i] == -5.5) {
                        cashModel.amount = 0.0
                        cashModel.labelAmount = "Custom"
                    } else {
                        cashModel.labelAmount = twoDigitString(currencyArray[i])
                        cashModel.amount = twoDigitDouble(currencyArray[i])
                    }
                    cashList.add(cashModel)
                }
                if (i == 2) {
                    if (currencyArray[i] == -5.5) {
                        cashModel.amount = 0.0
                        cashModel.labelAmount = "Custom"
                    } else {
                        cashModel.labelAmount = twoDigitString(currencyArray[i])
                        cashModel.amount = twoDigitDouble(currencyArray[i])
                    }
                    cashList.add(cashModel)
                }
                if (i == 3) {
                    if (currencyArray[i] == -5.5) {
                        cashModel.amount = 0.0
                        cashModel.labelAmount = "Custom"
                    } else {
                        cashModel.labelAmount = twoDigitString(currencyArray[i])
                        cashModel.amount = twoDigitDouble(currencyArray[i])
                    }
                    cashList.add(cashModel)
                }
                if (i == 4) {
                    if (currencyArray[i] == -5.5) {
                        cashModel.amount = 0.0
                        cashModel.labelAmount = "Custom"
                    } else {
                        cashModel.labelAmount = twoDigitString(currencyArray[i])
                        cashModel.amount = twoDigitDouble(currencyArray[i])
                    }
                    cashList.add(cashModel)
                }
                if (i == 5) {
                    if (currencyArray[i] == -5.5) {
                        cashModel.amount = 0.0
                        cashModel.labelAmount = "Custom"
                    } else {
                        cashModel.labelAmount = twoDigitString(currencyArray[i])
                        cashModel.amount = twoDigitDouble(currencyArray[i])
                    }
                    cashList.add(cashModel)
                }
            }
        }
        return cashList
    }


    fun String.toPriceAmount(): String {
        val dec = DecimalFormat("###,###,###.00")
        return dec.format(this.toDouble())
    }

    fun Double.toPriceAmount(): String {
        val dec = DecimalFormat("###,###,###.00")
        return dec.format(this)
    }

}

data class CashModel(
    var labelAmount: String = "",
    var amount: Double = 0.0
)