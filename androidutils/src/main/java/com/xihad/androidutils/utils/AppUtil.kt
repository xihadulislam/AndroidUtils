package com.xihad.androidutils.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.SystemClock

object AppUtil {
    var mLastClickTime = 0L

    fun isOpenRecently(): Boolean {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return true
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        return false
    }

    var lastClickTime = 0L
    fun isOpenLastSecond(): Boolean {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return true
        }
        lastClickTime = SystemClock.elapsedRealtime()
        return false
    }


    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var result = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }

        return result
    }


}