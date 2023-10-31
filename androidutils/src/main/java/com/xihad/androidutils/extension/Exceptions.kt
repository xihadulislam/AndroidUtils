package com.xihad.androidutils.extension

import java.io.IOException

class ApiException(message: String) : IOException(message)

class AppApiException(message: String, private val code: String) : IOException(message) {
    fun getErrorCode(): String {
        return code
    }
}

class NoInternetException(message: String) : IOException(message)