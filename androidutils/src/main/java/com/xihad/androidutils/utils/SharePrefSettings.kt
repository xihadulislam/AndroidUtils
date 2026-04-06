package com.xihad.androidutils.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharePrefSettings private constructor(context: Context) {

    private val preference: SharedPreferences =
        context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    fun getString(key: String, default: String = ""): String =
        preference.getString(key, default) ?: default

    fun setString(key: String, value: String) =
        preference.edit().putString(key, value).apply()

    fun getInt(key: String, default: Int = 0): Int =
        preference.getInt(key, default)

    fun setInt(key: String, value: Int) =
        preference.edit().putInt(key, value).apply()

    fun getLong(key: String, default: Long = 0L): Long =
        preference.getLong(key, default)

    fun setLong(key: String, value: Long) =
        preference.edit().putLong(key, value).apply()

    fun getFloat(key: String, default: Float = 0f): Float =
        preference.getFloat(key, default)

    fun setFloat(key: String, value: Float) =
        preference.edit().putFloat(key, value).apply()

    fun getDouble(key: String, default: Double = 0.0): Double =
        java.lang.Double.longBitsToDouble(preference.getLong(key, java.lang.Double.doubleToLongBits(default)))

    fun setDouble(key: String, value: Double) =
        preference.edit().putLong(key, java.lang.Double.doubleToRawLongBits(value)).apply()

    fun getBoolean(key: String, default: Boolean = false): Boolean =
        preference.getBoolean(key, default)

    fun setBoolean(key: String, value: Boolean) =
        preference.edit().putBoolean(key, value).apply()

    fun remove(key: String) = preference.edit().remove(key).apply()

    fun clear() = preference.edit().clear().apply()

    fun contains(key: String): Boolean = preference.contains(key)

    fun clearExcept(keep: Map<String, Any>) {
        clear()
        keep.forEach { (k, v) ->
            when (v) {
                is String -> setString(k, v)
                is Int -> setInt(k, v)
                is Long -> setLong(k, v)
                is Float -> setFloat(k, v)
                is Double -> setDouble(k, v)
                is Boolean -> setBoolean(k, v)
            }
        }
    }

    // ── Legacy API aliases (backwards-compatible) ────────────────────────────

    fun setStringValue(key: String, value: String) = setString(key, value)
    fun getStringValue(key: String): String = getString(key)
    fun getStringValue(key: String, default: String): String = getString(key, default)

    fun setIntValue(key: String, value: Int) = setInt(key, value)
    fun getIntValue(key: String): Int = getInt(key)
    fun getIntValue(key: String, default: Int): Int = getInt(key, default)

    fun setLongValue(key: String, value: Long) = setLong(key, value)
    fun getLongValue(key: String): Long = getLong(key)
    fun getLongValue(key: String, default: Long): Long = getLong(key, default)

    fun setFloatValue(key: String, value: Float) = setFloat(key, value)
    fun getFloatValue(key: String): Float = getFloat(key)
    fun getFloatValue(key: String, default: Float): Float = getFloat(key, default)

    fun setDoubleValue(key: String, value: Double) = setDouble(key, value)
    fun getDoubleValue(key: String): Double = getDouble(key)

    fun setBoolValue(key: String, value: Boolean) = setBoolean(key, value)
    fun getBoolValue(key: String): Boolean = getBoolean(key)
    fun getBoolValue(key: String, default: Boolean): Boolean = getBoolean(key, default)

    fun removeKeyValue(key: String) = remove(key)
    fun clearData() = clear()
    fun onResetExcept(data: MutableMap<String, Any>) = clearExcept(data)

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: SharePrefSettings? = null

        fun getInstance(context: Context): SharePrefSettings =
            instance ?: synchronized(this) {
                instance ?: SharePrefSettings(context.applicationContext).also { instance = it }
            }
    }
}
