package com.xihad.androidutils.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

object ColorUtil {

    fun getColoredDrawable(context: Context, drawableRef: Int, @ColorInt color: Int): Drawable {
        val drawable = ContextCompat.getDrawable(context, drawableRef)!!.mutate()
        drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        return drawable
    }

    fun setStatusBarColor(activity: Activity, @ColorRes colorRes: Int) {
        @Suppress("DEPRECATION")
        activity.window.statusBarColor = ContextCompat.getColor(activity, colorRes)
    }

    fun getRandomColorList(): List<Int> = listOf(
        Color.parseColor("#EBFAEE"),
        Color.parseColor("#FCF5D5"),
        Color.parseColor("#E3ECFF"),
        Color.parseColor("#FFD1DA"),
        Color.parseColor("#F6E6FF")
    ).shuffled()

    fun getRandomColor(): Int = getRandomColorList().first()

    fun TextView.setDrawableColor(@ColorRes colorRes: Int) {
        val filter = PorterDuffColorFilter(
            ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN
        )
        compoundDrawablesRelative.filterNotNull().forEach { it.colorFilter = filter }
    }

    fun ImageView.setTint(@ColorInt color: Int?) {
        if (color == null) {
            ImageViewCompat.setImageTintList(this, null)
        } else {
            ImageViewCompat.setImageTintMode(this, PorterDuff.Mode.SRC_ATOP)
            ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
        }
    }

    /** Parses a hex color string (with or without #) and returns RGB as a Triple. */
    fun String.hexToRGB(): Triple<Int, Int, Int> {
        val hex = if (startsWith("#")) this else "#$this"
        val color = Color.parseColor(hex)
        return Triple(Color.red(color), Color.green(color), Color.blue(color))
    }

    fun Int.colorToHexString(): String = String.format("#%06X", 0xFFFFFF and this)

    /** Lightens a color by the given [factor] (0.0–1.0). */
    fun Int.lighten(factor: Float = 0.2f): Int {
        val r = ((Color.red(this) + (255 - Color.red(this)) * factor).coerceIn(0f, 255f)).toInt()
        val g = ((Color.green(this) + (255 - Color.green(this)) * factor).coerceIn(0f, 255f)).toInt()
        val b = ((Color.blue(this) + (255 - Color.blue(this)) * factor).coerceIn(0f, 255f)).toInt()
        return Color.rgb(r, g, b)
    }

    /** Darkens a color by the given [factor] (0.0–1.0). */
    fun Int.darken(factor: Float = 0.2f): Int {
        val r = (Color.red(this) * (1 - factor)).coerceIn(0f, 255f).toInt()
        val g = (Color.green(this) * (1 - factor)).coerceIn(0f, 255f).toInt()
        val b = (Color.blue(this) * (1 - factor)).coerceIn(0f, 255f).toInt()
        return Color.rgb(r, g, b)
    }

    /** Returns true if the color is considered dark (useful for choosing text color). */
    fun Int.isDark(): Boolean {
        val luminance = (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
        return luminance < 0.5
    }
}
