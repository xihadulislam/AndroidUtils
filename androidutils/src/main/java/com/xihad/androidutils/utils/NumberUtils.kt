package com.xihad.androidutils.utils

import java.text.DecimalFormat

object NumberUtils {

    fun Float.roundOff(): String {
        val df = DecimalFormat("##.##")
        return df.format(this)
    }

    fun Double.roundOff(): String {
        val df = DecimalFormat("##.##")
        return df.format(this)
    }


    fun Int.appendOrdinal(): String {
        return ordinalOf(this)
    }

    private fun ordinalOf(i: Int) = "$i" + if (i % 100 in 11..13) "th" else when (i % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }


    fun String.enDigitToBn(): String {
        val bnDigits = listOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        return this.map { bnDigits[it.toString().toInt()] }.joinToString("")
    }


    fun getDigitEnglishFromBangla(number: String): String {
        val banglaToEnglishDigitsMap: MutableMap<Char, Char> = HashMap()
        banglaToEnglishDigitsMap['০'] = '0'
        banglaToEnglishDigitsMap['১'] = '1'
        banglaToEnglishDigitsMap['২'] = '2'
        banglaToEnglishDigitsMap['৩'] = '3'
        banglaToEnglishDigitsMap['৪'] = '4'
        banglaToEnglishDigitsMap['৫'] = '5'
        banglaToEnglishDigitsMap['৬'] = '6'
        banglaToEnglishDigitsMap['৭'] = '7'
        banglaToEnglishDigitsMap['৮'] = '8'
        banglaToEnglishDigitsMap['৯'] = '9'
        val builder = java.lang.StringBuilder()
        try {
            for (i in number.indices) {
                if (banglaToEnglishDigitsMap.containsKey(number[i])) {
                    builder.append(banglaToEnglishDigitsMap[number[i]])
                } else {
                    builder.append(number[i])
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return builder.toString()
    }


    fun getDigitBanglaFromEnglish(number: String): String {
        val banglaDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')

        val builder = StringBuilder()
        try {
            for (i in number.indices) {
                if (Character.isDigit(number[i])) {
                    if (number[i].toInt() - 48 <= 9) {
                        builder.append(banglaDigits[number[i].toInt() - 48])
                    } else {
                        builder.append(number[i])
                    }
                } else {
                    builder.append(number[i])
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return builder.toString()
    }

    fun numberInBangla(number: String): String {

        return number.replace("0", "০").replace("1", "১").replace("2", "২")
            .replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬")
            .replace("7", "৭").replace("8", "৮").replace("9", "৯")

    }


    fun numberToWords(number: Long): String {
        var limit = 1000000000000L
        var currHun: Long
        var t: Long = 0

        if (number == 0L) return "Zero"

        val multiplier = arrayOf("", "Trillion", "Billion", "Million", "Thousand")

        val firstTwenty = arrayOf(
            "", "One", "Two", "Three",
            "Four", "Five", "Six", "Seven",
            "Eight", "Nine", "Ten", "Eleven",
            "Twelve", "Thirteen", "Fourteen", "Fifteen",
            "Sixteen", "Seventeen", "Eighteen", "Nineteen"
        )

        val tens = arrayOf(
            "", "Twenty", "Thirty",
            "Forty", "Fifty", "Sixty",
            "Seventy", "Eighty", "Ninety"
        )

        if (number < 20L) return firstTwenty[number.toInt()]
        var answer = ""
        var i = number
        while (i > 0) {
            currHun = i / limit

            while (currHun == 0L) {
                i %= limit
                limit /= 1000
                currHun = i / limit
                ++t
            }

            if (currHun > 99) answer += (firstTwenty[currHun.toInt() / 100]
                    + " Hundred ")

            currHun %= 100


            if (currHun in 1..19) answer += firstTwenty[currHun.toInt()] + " " else if (currHun % 10 == 0L && currHun != 0L) answer += tens[currHun.toInt() / 10 - 1] + " " else if (currHun in 21..99) answer += (tens[currHun.toInt() / 10 - 1] + " "
                    + firstTwenty[currHun.toInt() % 10]
                    + " ")

            if (t < 4) answer += multiplier[(++t).toInt()] + " "
            i %= limit
            limit /= 1000
        }
        return answer
    }


}