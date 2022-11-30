package com.xihad.androidutils.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Utils {

    fun postDelayed(milliSecond: Long, func: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            func()
        }, milliSecond)
    }


    fun splitString(str: String, limit: Int): String {
        var subString = ""
        if (str.isNotEmpty() && limit > 0) {
            subString = str.substring(0, limit)
        }
        return subString
    }

    fun roundOffDecimal(number: Float): Float {
        return try {
            val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.US))
            df.format(number).toFloat()
        } catch (e: Exception) {
            number
        }
    }


    fun getJsonFromAsset(context: Context, fileName: String): String {
        var jsonString = ""
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return jsonString
        }
        return jsonString
    }


    fun uId(): String {
        var id = ""
        val tz = TimeZone.getTimeZone("Europe/London")
        val cal = Calendar.getInstance(tz)
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DATE]
        val hourOfDay = cal[Calendar.HOUR_OF_DAY]
        val minutes = cal[Calendar.MINUTE]
        val seconds = cal[Calendar.SECOND]
        val mileSeconds = cal[Calendar.MILLISECOND]
        val newMileSecond = String.format("%03d", mileSeconds)
        val newSecond = String.format("%02d", seconds)
        val newMinute = String.format("%02d", minutes)
        val newDay = String.format("%02d", day)
        val yearCal = year + 20
        val monthCal = month + 26
        val hourCal = hourOfDay + 28
        val newYEar = yearCal.toString().substring(2)
        val AtoZValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val ZerotoNindValue = "0123456789"
        val RendomNoForYear = Random().nextInt(26)
        val RendomNoForday = Random().nextInt(10)
        val RendomNoForhour = Random().nextInt(26)
        val RendomNoForCharone = Random().nextInt(26)
        val rendomNoForChartwo = Random().nextInt(26)
        val yearChar = AtoZValue[RendomNoForYear]
        val dayChar = ZerotoNindValue[RendomNoForday]
        val hourChar = AtoZValue[RendomNoForhour]
        val oneChar = AtoZValue[RendomNoForCharone]
        val twoChar = AtoZValue[rendomNoForChartwo]
        (newYEar + yearChar + "" + monthCal + ""
                + newDay + dayChar + "" + hourChar + hourCal
                + "" + newMinute + "" + newSecond
                + "" + oneChar + "" + twoChar
                ).also {
                id = it
            }

        return id
    }


}