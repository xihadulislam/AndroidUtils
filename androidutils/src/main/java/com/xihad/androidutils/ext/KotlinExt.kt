package com.xihad.androidutils.ext

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.TintAwareDrawable
import java.text.DecimalFormat
import java.util.regex.Pattern

object KotlinExt {

    fun String.isEmailValid(): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        return pattern.matcher(this).matches()
    }

    /**
     * Returns the last path component of a URL or file path.
     *   "https://google.com/chrome/".lastPathComponent → "chrome"
     *   "C:\\Windows\\Fonts\\font.ttf".lastPathComponent → "font.ttf"
     */
    val String.lastPathComponent: String
        get() {
            var path = trimEnd('/', '\\')
            var index = path.lastIndexOf('/')
            if (index < 0) index = path.lastIndexOf('\\')
            return if (index < 0) path else path.substring(index + 1)
        }

    /** Formats a raw digit string as a credit card number (groups of 4). */
    val String.creditCardFormatted: String
        get() {
            val clean = replace(" ", "").trim()
            return clean.chunked(4).joinToString(" ")
        }

    fun String.sortAlphabetically() = toCharArray().apply { sort() }

    fun ImageView.tintSrc(@ColorRes colorRes: Int) {
        val drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorRes))
        setImageDrawable(drawable)
        if (drawable is TintAwareDrawable) invalidate()
    }

    /** Compat version of setExactAndAllowWhileIdle(). */
    fun AlarmManager.setExactAndAllowWhileIdleCompat(
        alarmType: Int,
        timeMillis: Long,
        pendingIntent: PendingIntent
    ) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ->
                setExactAndAllowWhileIdle(alarmType, timeMillis, pendingIntent)
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ->
                setExact(alarmType, timeMillis, pendingIntent)
            else -> set(alarmType, timeMillis, pendingIntent)
        }
    }

    /** Sets the status bar color. Uses WindowInsetsController on API 30+. */
    fun Activity.setStatusBarColor(@ColorRes colorRes: Int) {
        @Suppress("DEPRECATION")
        window.statusBarColor = ContextCompat.getColor(this, colorRes)
    }

    /** Makes the window layout extend under the status bar. */
    fun Activity.setFullscreenLayoutFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun String.toPriceAmount(): String = DecimalFormat("###,###,###.00").format(this.toDouble())

    fun Double.toPriceAmount(): String = DecimalFormat("###,###,###.00").format(this)
}
