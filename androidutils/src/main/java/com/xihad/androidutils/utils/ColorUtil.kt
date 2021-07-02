package com.xihad.androidutils.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat

object ColorUtil {

    fun setStatusBarColor(activity: Activity,color: Int) {
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


}