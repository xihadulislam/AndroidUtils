package com.xihad.androidutils.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

object ColorUtil {


    fun getColoredDrawable(context: Context, drawableRef: Int, color: Int): Drawable {
        val drawable = ContextCompat.getDrawable(context, drawableRef)!!.mutate()
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        return drawable
    }

    fun setStatusBarColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.statusBarColor = ContextCompat.getColor(activity, color)
        }
    }

    fun getRandomColorList(): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        list.add(Color.parseColor("#EBFAEE"))
        list.add(Color.parseColor("#FCF5D5"))
        list.add(Color.parseColor("#E3ECFF"))
        list.add(Color.parseColor("#FFD1DA"))
        list.add(Color.parseColor("#F6E6FF"))
        list.shuffle()
        return list
    }


    fun getRandomColor(): Int {
        val list = mutableListOf<Int>()
        list.add(Color.parseColor("#EBFAEE"))
        list.add(Color.parseColor("#FCF5D5"))
        list.add(Color.parseColor("#E3ECFF"))
        list.add(Color.parseColor("#FFD1DA"))
        list.add(Color.parseColor("#F6E6FF"))
        list.shuffle()
        list.shuffle()
        return list[0]
    }


    fun TextView.setDrawableColor(@ColorRes color: Int) {
        compoundDrawablesRelative.filterNotNull().forEach {
            it.colorFilter = PorterDuffColorFilter(
                ContextCompat.getColor(this.context, color),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    fun ImageView.setTint(@ColorInt color: Int?) {
        if (color == null) {
            ImageViewCompat.setImageTintList(this, null)
            return
        }
        ImageViewCompat.setImageTintMode(this, PorterDuff.Mode.SRC_ATOP)
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
    }


    fun String.hexToRGB(): Triple<String, String, String> {
        var name = this
        if (!name.startsWith("#")) {
            name = "#$this"
        }
        val color = Color.parseColor(name)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        return Triple(red.toString(), green.toString(), blue.toString())
    }

    fun Int.colorToHexString(): String {
        return String.format("#%06X", -0x1 and this).replace("#FF", "#")
    }

}