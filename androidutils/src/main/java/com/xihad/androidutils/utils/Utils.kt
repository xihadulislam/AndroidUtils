package com.xihad.androidutils.utils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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