package com.xihad.androidutils.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.SystemClock
import android.view.View
import java.util.concurrent.atomic.AtomicLong

object AppUtil {

    fun View.onClick(debounceDuration: Long = 300L, action: (View) -> Unit) {
        setOnClickListener(DebouncedOnClickListener(debounceDuration) { action(it) })
    }

    private class DebouncedOnClickListener(
        private val debounceDuration: Long,
        private val clickAction: (View) -> Unit
    ) : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val now = SystemClock.elapsedRealtime()
            if (now - lastClickTime >= debounceDuration) {
                lastClickTime = now
                clickAction(v)
            }
        }
    }

    private val lastClickTimestamp = AtomicLong(0L)

    /** Returns true if called within 1 second of the previous call. */
    fun isOpenRecently(thresholdMs: Long = 1000L): Boolean {
        val now = SystemClock.elapsedRealtime()
        val last = lastClickTimestamp.get()
        if (now - last < thresholdMs) return true
        lastClickTimestamp.compareAndSet(last, now)
        return false
    }

    fun isInternetAvailable(context: Context): Boolean {
        val cm = context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = cm.activeNetwork ?: return false
            val caps = cm.getNetworkCapabilities(network) ?: return false
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            cm.activeNetworkInfo?.isConnected == true
        }
    }

    fun Context?.isOnline(): Boolean {
        this ?: return false
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = cm.activeNetwork ?: return false
            val caps = cm.getNetworkCapabilities(network) ?: return false
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            cm.activeNetworkInfo?.isConnected == true
        }
    }

    fun Context?.isOnline(
        failBlock: () -> Unit = {},
        successBlock: () -> Unit
    ) {
        if (this?.isOnline() == true) successBlock() else failBlock()
    }
}
