package com.xihad.androidutils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.provider.Settings
import android.util.Log

object DeviceInfoUtil {

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.lowercase().startsWith(manufacturer.lowercase())) {
            capitalize(model)
        } else {
            "${capitalize(manufacturer)} $model"
        }
    }

    private fun capitalize(s: String?): String {
        if (s.isNullOrEmpty()) return ""
        return s[0].uppercaseChar() + s.substring(1)
    }

    fun getDeviceSuperInfo(): String {
        val sb = StringBuilder("Debug-infos:")
        try {
            sb.appendLine("\n OS Version: ${System.getProperty("os.version")} (${Build.VERSION.INCREMENTAL})")
            sb.appendLine(" OS API Level: ${Build.VERSION.SDK_INT}")
            sb.appendLine(" Device: ${Build.DEVICE}")
            sb.appendLine(" Model (and Product): ${Build.MODEL} (${Build.PRODUCT})")
            sb.appendLine(" RELEASE: ${Build.VERSION.RELEASE}")
            sb.appendLine(" BRAND: ${Build.BRAND}")
            sb.appendLine(" DISPLAY: ${Build.DISPLAY}")
            sb.appendLine(" SUPPORTED_ABIS: ${Build.SUPPORTED_ABIS.joinToString()}")
            sb.appendLine(" HARDWARE: ${Build.HARDWARE}")
            sb.appendLine(" Build ID: ${Build.ID}")
            sb.appendLine(" MANUFACTURER: ${Build.MANUFACTURER}")
            sb.appendLine(" USER: ${Build.USER}")
            sb.appendLine(" HOST: ${Build.HOST}")
        } catch (e: Exception) {
            Log.e("DeviceInfoUtil", "Error getting device info", e)
        }
        return sb.toString()
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(activity: Activity): String {
        return Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getSupportedAbis(): Array<String> = Build.SUPPORTED_ABIS

    fun getPrimaryAbi(): String = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown"

    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic")
                || Build.DEVICE.startsWith("generic"))
    }
}
