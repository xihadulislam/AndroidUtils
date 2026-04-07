package com.xihad.androidutils.utils

import android.util.Log
import kotlin.text.substring

/**
 * Created by @Ziad Islam on 05/11/2025.
 */

object Log {

    const val TAG = "AndroidUtils"
    private const val MAX_LOG_LENGTH = 4000

    val isDebug: Boolean get() = true

    // ========== Standard logging (EXISTING CODE - NO CHANGES NEEDED) ==========
    @JvmStatic
    fun d(message: String?) {
        if (isDebug) logLong(TAG, message ?: "null", Log.DEBUG)
    }

    @JvmStatic
    fun e(message: String?) {
        if (isDebug) logLong(TAG, message ?: "null", Log.ERROR)
    }

    @JvmStatic
    fun i(message: String?) {
        if (isDebug) logLong(TAG, message ?: "null", Log.INFO)
    }

    @JvmStatic
    fun w(message: String?) {
        if (isDebug) logLong(TAG, message ?: "null", Log.WARN)
    }

    @JvmStatic
    fun v(message: String?) {
        if (isDebug) logLong(TAG, message ?: "null", Log.VERBOSE)
    }

    // ========== Two arguments - custom tag (EXISTING CODE - NO CHANGES NEEDED) ==========

    @JvmStatic
    fun d(tag: String?, message: String?) {
        if (isDebug) logLong(tag ?: TAG, message ?: "null", Log.DEBUG)
    }

    @JvmStatic
    fun e(tag: String?, message: String?) {
        if (isDebug) logLong(tag ?: TAG, message ?: "null", Log.ERROR)
    }

    @JvmStatic
    fun i(tag: String?, message: String?) {
        if (isDebug) logLong(tag ?: TAG, message ?: "null", Log.INFO)
    }

    @JvmStatic
    fun w(tag: String?, message: String?) {
        if (isDebug) logLong(tag ?: TAG, message ?: "null", Log.WARN)
    }

    @JvmStatic
    fun v(tag: String?, message: String?) {
        if (isDebug) logLong(tag ?: TAG, message ?: "null", Log.VERBOSE)
    }

    // ========== Exception logging (EXISTING CODE - NO CHANGES NEEDED) ==========

    @JvmStatic
    fun e(message: String?, throwable: Throwable?) {
        if (isDebug) {
            Log.e(TAG, message ?: "null", throwable)
        }
    }

    @JvmStatic
    fun e(tag: String?, message: String?, throwable: Throwable?) {
        if (isDebug) {
            Log.e(tag ?: TAG, message ?: "null", throwable)
        }
    }

    @JvmStatic
    fun w(tag: String?, message: String?, throwable: Throwable?) {
        if (isDebug) {
            Log.w(tag ?: TAG, message ?: "null", throwable)
        }
    }

    // ========== NEW: Lazy evaluation (OPTIONAL - for future optimization) ==========

    inline fun dLazy(crossinline messageProvider: () -> String?) {
        if (isDebug) logLong(TAG, messageProvider() ?: "null", Log.DEBUG)
    }

    inline fun eLazy(crossinline messageProvider: () -> String?) {
        if (isDebug) logLong(TAG, messageProvider() ?: "null", Log.ERROR)
    }

    inline fun iLazy(crossinline messageProvider: () -> String?) {
        if (isDebug) logLong(TAG, messageProvider() ?: "null", Log.INFO)
    }

    inline fun wLazy(crossinline messageProvider: () -> String?) {
        if (isDebug) logLong(TAG, messageProvider() ?: "null", Log.WARN)
    }

    inline fun vLazy(crossinline messageProvider: () -> String?) {
        if (isDebug) logLong(TAG, messageProvider() ?: "null", Log.VERBOSE)
    }

    // ========== NEW: JSON logging (OPTIONAL - lazy by default) ==========

    @JvmStatic
    inline fun json(crossinline jsonProvider: () -> String?) {
        if (isDebug) {
            val json = jsonProvider() ?: "null"
            logLong(TAG, "JSON: $json", Log.DEBUG)
        }
    }

    @JvmStatic
    inline fun json(tag: String?, crossinline jsonProvider: () -> String?) {
        if (isDebug) {
            val json = jsonProvider() ?: "null"
            logLong(tag ?: TAG, "JSON: $json", Log.DEBUG)
        }
    }

    // ========== Helper: Split long messages ==========

    fun logLong(tag: String, message: String, level: Int) {
        if (message.length <= MAX_LOG_LENGTH) {
            when (level) {
                Log.DEBUG -> Log.d(tag, message)
                Log.ERROR -> Log.e(tag, message)
                Log.INFO -> Log.i(tag, message)
                Log.WARN -> Log.w(tag, message)
                Log.VERBOSE -> Log.v(tag, message)
            }
        } else {
            // Split long messages into chunks
            var i = 0
            while (i < message.length) {
                val end = kotlin.comparisons.minOf(i + MAX_LOG_LENGTH, message.length)
                val chunk = message.substring(i, end)
                when (level) {
                    Log.DEBUG -> Log.d(tag, chunk)
                    Log.ERROR -> Log.e(tag, chunk)
                    Log.INFO -> Log.i(tag, chunk)
                    Log.WARN -> Log.w(tag, chunk)
                    Log.VERBOSE -> Log.v(tag, chunk)
                }
                i = end
            }
        }
    }
}