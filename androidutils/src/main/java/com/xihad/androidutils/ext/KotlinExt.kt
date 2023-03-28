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
import java.security.MessageDigest
import java.text.DecimalFormat
import java.util.regex.Pattern

object KotlinExt {



    fun String.isEmailValid(): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(this)
        return matcher.matches()
    }


    /**
     *
     * How to use it
    val lpc1 = "https://google.com/chrome/".lastPathComponent // chrome
    val lpc2 = "C:\\Windows\\Fonts\\font.ttf".lastPathComponent // font.ttf
    val lpc3 = "/dev/null".lastPathComponent // null
     */

    val String.lastPathComponent: String
        get() {
            var path = this
            if (path.endsWith("/"))
                path = path.substring(0, path.length - 1)
            var index = path.lastIndexOf('/')
            if (index < 0) {
                if (path.endsWith("\\"))
                    path = path.substring(0, path.length - 1)
                index = path.lastIndexOf('\\')
                if (index < 0)
                    return path
            }
            return path.substring(index + 1)
        }


    val String.creditCardFormatted: String
        get() {
            val preparedString = replace(" ", "").trim()
            val result = StringBuilder()
            for (i in preparedString.indices) {
                if (i % 4 == 0 && i != 0) {
                    result.append(" ")
                }
                result.append(preparedString[i])
            }
            return result.toString()
        }


    fun String.sortAlphabetically() = toCharArray().apply { sort() }


    fun ImageView.tintSrc(@ColorRes colorRes: Int) {
        val drawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorRes))
        setImageDrawable(drawable)
        if (drawable is TintAwareDrawable) invalidate() // Because in this case setImageDrawable will not call invalidate()
    }


    /**
     * Compat version of setExactAndAllowWhileIdle()
     */
    fun AlarmManager.setExactAndAllowWhileIdleCompat(
        alarmType: Int,
        timeMillis: Long,
        pendingIntent: PendingIntent
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // This version added Doze
            setExactAndAllowWhileIdle(alarmType, timeMillis, pendingIntent)

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // This version changed set() to be inexact
            setExact(alarmType, timeMillis, pendingIntent)

        } else {
            set(alarmType, timeMillis, pendingIntent)
        }
    }


    // Helps to set status bar color with api version check
    fun Activity.setStatusBarColor(@ColorRes colorRes: Int): Unit {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, colorRes)
        }
    }

    // Adds flags to make window fullscreen
    fun Activity.setFullscreenLayoutFlags() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
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