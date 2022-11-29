package com.xihad.androidutils.utils;

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {

    fun getCurrentTime(): Long = System.currentTimeMillis()

    fun getCurrentTimeAndDate(): Date = Calendar.getInstance().time


    fun parseDate(
        dateString: String,
        fromPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
    ): Date? = SimpleDateFormat(fromPattern, Locale.getDefault()).parse(dateString)

    fun milliSecondToHM(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    fun getToday(): String {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy_MM_dd", Locale.US)
        return df.format(c)
    }


}
