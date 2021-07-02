package com.xihad.androidutils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.util.*

object DeviceInfoUtil {


    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.toLowerCase(Locale.ROOT)
                .startsWith(manufacturer.toLowerCase(Locale.ROOT))
        ) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    fun getDeviceSuperInfo(): String {

        var s = ""

        try {
            s = "Debug-infos:"
            s += """
         OS Version: ${System.getProperty("os.version")}(${Build.VERSION.INCREMENTAL})"""
            s += """
         OS API Level: ${Build.VERSION.SDK_INT}"""
            s += """
         Device: ${Build.DEVICE}"""
            s += """
         Model (and Product): ${Build.MODEL} (${Build.PRODUCT})"""
            s += """
         RELEASE: ${Build.VERSION.RELEASE}"""
            s += """
         BRAND: ${Build.BRAND}"""
            s += """
         DISPLAY: ${Build.DISPLAY}"""
            s += """
         CPU_ABI: ${Build.CPU_ABI}"""
            s += """
         CPU_ABI2: ${Build.CPU_ABI2}"""
            s += """
         UNKNOWN: ${Build.UNKNOWN}"""
            s += """
         HARDWARE: ${Build.HARDWARE}"""
            s += """
         Build ID: ${Build.ID}"""
            s += """
         MANUFACTURER: ${Build.MANUFACTURER}"""
            s += """
         USER: ${Build.USER}"""
            s += """
         HOST: ${Build.HOST}"""

        } catch (e: java.lang.Exception) {
            Log.e("tag", "Error getting Device INFO")
        }

        return s
    }


    @SuppressLint("HardwareIds")
    fun getDeviceId(activity: Activity): String {
        return Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
    }


}