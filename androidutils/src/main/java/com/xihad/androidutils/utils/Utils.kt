package com.xihad.androidutils.utils

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.view.View
import com.xihad.androidutils.effect.ClickEffect
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object Utils {

    val Int.pxTodp: Int get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.dpTopx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun validateEmailAddress(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Runs [func] on the main thread after [milliSecond] delay.
     *
     * WARNING: If [func] captures an Activity or Fragment reference and the
     * component is destroyed before the delay expires, that reference will be
     * kept alive until the callback fires. Use a WeakReference in the lambda
     * when the delay is significant, e.g.:
     *
     *   val ref = WeakReference(this)
     *   postDelayed(3000) { ref.get()?.doSomething() }
     */
    fun postDelayed(milliSecond: Long, func: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed(func, milliSecond)
    }

    fun splitString(str: String, limit: Int): String {
        if (str.isEmpty() || limit <= 0) return ""
        return str.take(limit)
    }

    fun roundOffDecimal(number: Float): Float = try {
        DecimalFormat("#.#", DecimalFormatSymbols(Locale.US)).format(number).toFloat()
    } catch (e: Exception) {
        number
    }

    fun getJsonFromAsset(context: Context, fileName: String): String = try {
        context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        ""
    }

    fun uId(): String {
        val tz = TimeZone.getTimeZone("Europe/London")
        val cal = Calendar.getInstance(tz)
        val az = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val zn = "0123456789"
        val rnd = Random()
        return buildString {
            append((cal[Calendar.YEAR] + 20).toString().substring(2))
            append(az[rnd.nextInt(26)])
            append(cal[Calendar.MONTH] + 26)
            append(String.format("%02d", cal[Calendar.DATE]))
            append(zn[rnd.nextInt(10)])
            append(az[rnd.nextInt(26)])
            append(cal[Calendar.HOUR_OF_DAY] + 28)
            append(String.format("%02d", cal[Calendar.MINUTE]))
            append(String.format("%02d", cal[Calendar.SECOND]))
            append(az[rnd.nextInt(26)])
            append(az[rnd.nextInt(26)])
        }
    }

    fun applyClickEffect(view: View) { ClickEffect(view) }
}
