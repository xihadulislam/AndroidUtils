package com.xihad.androidutils.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow

object NumberUtils {

    fun Float.roundOff(digits: Int = 2): String = DecimalFormat("##.${"#".repeat(digits)}").format(this)

    fun Double.roundOff(digits: Int = 2): String = DecimalFormat("##.${"#".repeat(digits)}").format(this)

    fun Int.appendOrdinal(): String {
        val suffix = if (this % 100 in 11..13) "th" else when (this % 10) {
            1 -> "st"; 2 -> "nd"; 3 -> "rd"; else -> "th"
        }
        return "$this$suffix"
    }

    /** Formats a number with thousand-separator commas: 1234567 → "1,234,567" */
    fun Long.formatWithCommas(): String = NumberFormat.getNumberInstance(Locale.US).format(this)
    fun Int.formatWithCommas(): String = NumberFormat.getNumberInstance(Locale.US).format(this)
    fun Double.formatWithCommas(): String = NumberFormat.getNumberInstance(Locale.US).format(this)

    /** Converts a byte count to a human-readable file size string (e.g. "1.2 MB"). */
    fun Long.formatBytes(): String {
        if (this < 1024) return "$this B"
        val exp = (log10(this.toDouble()) / log10(1024.0)).toInt()
        val prefix = "KMGTPE"[exp - 1]
        return "%.1f %sB".format(this / 1024.0.pow(exp.toDouble()), prefix)
    }

    /** Converts a digit-only string from English numerals to Bangla numerals. */
    fun String.enDigitToBn(): String {
        val bnDigits = listOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return map { ch ->
            if (ch.isDigit()) bnDigits[ch.digitToInt()] else ch
        }.joinToString("")
    }

    /** Converts Bangla digit characters in a string to English digits. */
    fun getDigitEnglishFromBangla(number: String): String {
        val map = mapOf(
            '০' to '0', '১' to '1', '২' to '2', '৩' to '3', '৪' to '4',
            '৫' to '5', '৬' to '6', '৭' to '7', '৮' to '8', '৯' to '9'
        )
        return number.map { map[it] ?: it }.joinToString("")
    }

    /** Converts English digit characters in a string to Bangla digits. */
    fun getDigitBanglaFromEnglish(number: String): String {
        val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return number.map { ch ->
            if (ch.isDigit()) banglaDigits[ch.code - 48] else ch
        }.joinToString("")
    }

    fun numberInBangla(number: String): String = getDigitBanglaFromEnglish(number)

    fun numberToWords(number: Long): String {
        if (number == 0L) return "Zero"
        if (number < 0L) return "Negative ${numberToWords(-number)}"

        val firstTwenty = arrayOf(
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
            "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        )
        val tens = arrayOf("", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")

        fun hundredsToWords(n: Long): String {
            if (n == 0L) return ""
            if (n < 20) return firstTwenty[n.toInt()] + " "
            if (n < 100) return tens[(n / 10).toInt() - 1] + " " +
                    (if (n % 10 != 0L) firstTwenty[(n % 10).toInt()] + " " else "")
            return firstTwenty[(n / 100).toInt()] + " Hundred " + hundredsToWords(n % 100)
        }

        val scales = listOf(1_000_000_000_000L to "Trillion", 1_000_000_000L to "Billion",
            1_000_000L to "Million", 1_000L to "Thousand", 1L to "")
        var remaining = number
        val result = StringBuilder()
        for ((scale, name) in scales) {
            if (remaining >= scale) {
                result.append(hundredsToWords(remaining / scale))
                if (name.isNotEmpty()) result.append("$name ")
                remaining %= scale
            }
        }
        return result.toString().trim()
    }
}
