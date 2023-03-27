package com.xihad.androidutils.utils

import android.util.Log

object LogUtils {

    private const val TAG = "LogUtils"

    fun String.debug() {
        Log.d(TAG, "debug: $this")
    }

    fun String.error() {
        Log.e(TAG, "error: $this")
    }

    fun String.info() {
        Log.i(TAG, "info: $this")
    }


    fun logAppend(str: String) {
        if (str.length > 4000) {
            logAppend(str.substring(4000))
            Log.d(TAG, "   " + str.substring(0, 4000))
        } else {
            Log.d(TAG, "   $str")
        }
    }


//    fun fileLog(msg: String) {
//
//    }

}