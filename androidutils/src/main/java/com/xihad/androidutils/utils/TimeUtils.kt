package com.xihad.androidutils.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeUtils {

    fun getCurrentTime(): Long = System.currentTimeMillis()

    fun getCurrentTimeAndDate(): Date = Date()

    fun parseDate(
        dateString: String,
        pattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ",
        locale: Locale = Locale.getDefault()
    ): Date? = runCatching {
        SimpleDateFormat(pattern, locale).parse(dateString)
    }.getOrNull()

    fun Date.format(
        pattern: String = "yyyy-MM-dd HH:mm:ss",
        locale: Locale = Locale.getDefault()
    ): String = SimpleDateFormat(pattern, locale).format(this)

    fun Long.toDate(): Date = Date(this)

    fun Long.toFormattedDate(
        pattern: String = "yyyy-MM-dd",
        locale: Locale = Locale.getDefault()
    ): String = SimpleDateFormat(pattern, locale).format(Date(this))

    fun milliSecondToHMS(millis: Long): String {
        val hours   = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(millis)
        )
        return "%02d:%02d:%02d".format(hours, minutes, seconds)
    }

    /** @deprecated Use milliSecondToHMS */
    fun milliSecondToHM(millis: Long): String = milliSecondToHMS(millis)

    fun getToday(pattern: String = "yyyy-MM-dd", locale: Locale = Locale.US): String =
        SimpleDateFormat(pattern, locale).format(Date())

    /** Returns the number of days between two dates (absolute value). */
    fun daysBetween(from: Date, to: Date): Long =
        TimeUnit.MILLISECONDS.toDays(Math.abs(to.time - from.time))

    /** Returns true if [this] date is today. */
    fun Date.isToday(): Boolean {
        val cal1 = Calendar.getInstance().apply { time = this@isToday }
        val cal2 = Calendar.getInstance()
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    /** Returns true if [this] date is in the past. */
    fun Date.isPast(): Boolean = before(Date())

    /** Returns true if [this] date is in the future. */
    fun Date.isFuture(): Boolean = after(Date())

    /** Returns a human-friendly relative time string ("2 hours ago", "in 3 days", etc.). */
    fun Long.toRelativeTime(): String {
        val diff = System.currentTimeMillis() - this
        val absDiff = Math.abs(diff)
        val suffix = if (diff >= 0) "ago" else "from now"
        return when {
            absDiff < TimeUnit.MINUTES.toMillis(1)  -> "just now"
            absDiff < TimeUnit.HOURS.toMillis(1)    -> "${TimeUnit.MILLISECONDS.toMinutes(absDiff)} min $suffix"
            absDiff < TimeUnit.DAYS.toMillis(1)     -> "${TimeUnit.MILLISECONDS.toHours(absDiff)} hr $suffix"
            absDiff < TimeUnit.DAYS.toMillis(7)     -> "${TimeUnit.MILLISECONDS.toDays(absDiff)} days $suffix"
            absDiff < TimeUnit.DAYS.toMillis(30)    -> "${TimeUnit.MILLISECONDS.toDays(absDiff) / 7} weeks $suffix"
            absDiff < TimeUnit.DAYS.toMillis(365)   -> "${TimeUnit.MILLISECONDS.toDays(absDiff) / 30} months $suffix"
            else                                     -> "${TimeUnit.MILLISECONDS.toDays(absDiff) / 365} years $suffix"
        }
    }
}
