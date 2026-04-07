package com.xihad.androidutils.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object StatusBarUtil {

    /**
     * Sets the status bar background color.
     * Works on all API levels supported by the library (21+).
     */
    fun Activity.setStatusBarColor(@ColorInt color: Int) {
        @Suppress("DEPRECATION")
        window.statusBarColor = color
    }

    /**
     * Sets the status bar icons to dark (for light backgrounds) or light (for dark backgrounds).
     * Uses WindowInsetsControllerCompat for compatibility.
     */
    fun Activity.setStatusBarIconsDark(dark: Boolean) {
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = dark
    }

    /**
     * Sets the navigation bar background color.
     */
    fun Activity.setNavigationBarColor(@ColorInt color: Int) {
        @Suppress("DEPRECATION")
        window.navigationBarColor = color
    }

    /**
     * Sets navigation bar icons to dark (for light backgrounds).
     */
    fun Activity.setNavigationBarIconsDark(dark: Boolean) {
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = dark
    }

    /**
     * Makes the activity draw edge-to-edge (content goes behind status and nav bars).
     * Call this in onCreate() before setContentView().
     */
    fun Activity.enableEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    /**
     * Makes the status bar fully transparent and draws content behind it.
     */
    fun Activity.makeStatusBarTransparent() {
        enableEdgeToEdge()
        setStatusBarColor(Color.TRANSPARENT)
    }

    /**
     * Hides the status bar.
     */
    fun Activity.hideStatusBar() {
        WindowInsetsControllerCompat(window, window.decorView)
            .hide(WindowInsetsCompat.Type.statusBars())
    }

    /**
     * Shows the status bar.
     */
    fun Activity.showStatusBar() {
        WindowInsetsControllerCompat(window, window.decorView)
            .show(WindowInsetsCompat.Type.statusBars())
    }

    /**
     * Hides both status bar and navigation bar (immersive mode).
     */
    fun Activity.enterImmersiveMode() {
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

    /**
     * Exits immersive mode and shows both status and navigation bars.
     */
    fun Activity.exitImmersiveMode() {
        WindowInsetsControllerCompat(window, window.decorView)
            .show(WindowInsetsCompat.Type.systemBars())
    }

    /**
     * Keeps the screen on as long as this activity is visible.
     */
    fun Activity.keepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * Allows the screen to turn off normally.
     */
    fun Activity.allowScreenOff() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * Returns the status bar height in pixels.
     */
    fun Activity.getStatusBarHeight(): Int {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

    /**
     * Returns the navigation bar height in pixels.
     */
    fun Activity.getNavigationBarHeight(): Int {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
}
