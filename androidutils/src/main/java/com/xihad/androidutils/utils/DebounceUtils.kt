package com.xihad.androidutils.utils

import android.os.Handler
import android.os.Looper

object DebounceUtils {

    private var runnable: Runnable? = null
    private var handler = Handler(Looper.getMainLooper())

    fun debounce50(callback: DebounceCallback) {
        debounce(callback, 50)
    }

    fun debounce100(callback: DebounceCallback) {
        debounce(callback, 100)
    }

    fun debounce200(callback: DebounceCallback) {
        debounce(callback, 200)
    }

    fun debounce300(callback: DebounceCallback) {
        debounce(callback, 300)
    }

    fun debounce500(callback: DebounceCallback) {
        debounce(callback, 500)
    }

    fun debounce1000(callback: DebounceCallback) {
        debounce(callback, 1000)
    }


    fun debounce(callback: DebounceCallback, millisecond: Long) {
        runnable?.let { handler.removeCallbacks(it) }
        runnable = Runnable { callback.run() }
        handler.postDelayed(runnable!!, millisecond)
    }

    interface DebounceCallback {
        fun run()
    }

}