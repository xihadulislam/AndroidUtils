package com.xihad.androidutils.utils

import android.util.Log

object LogUtils {

    private const val TAG = "LogUtils"

    fun debug(msg: String) {
        Log.d(TAG, "debug: $msg")
    }

    fun error(msg: String) {
        Log.e(TAG, "error: $msg")
    }

    fun info(msg: String) {
        Log.i(TAG, "info: $msg")
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