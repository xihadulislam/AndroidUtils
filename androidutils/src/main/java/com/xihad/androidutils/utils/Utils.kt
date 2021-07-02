package com.xihad.androidutils.utils

import android.app.Activity
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object Utils {

    fun milliSecondToHM(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
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

    fun getToday(): String {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy_MM_dd", Locale.US)
        return df.format(c)
    }


    fun getJsonFromAsset(activity: Activity, fileName: String): String {
        var jsonString = ""
        try {
            jsonString = activity.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return jsonString
        }
        return jsonString
    }


}