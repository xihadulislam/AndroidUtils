package com.xihad.androidutils.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


object KeyboardUtil {

    fun showKeyboard(activity: Activity, view: View? = activity.currentFocus) {
        val methodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }


    fun hideSoftKeyBoard(activity: Activity) {
        try {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}