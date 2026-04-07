package com.xihad.androidutils.utils

import android.os.Handler
import android.os.Looper

/**
 * Debounce utility — delays execution until the specified quiet period has passed.
 * Each caller should hold their own instance so debounces don't interfere.
 *
 * Kotlin:
 *   private val debounce = DebounceUtils()
 *   debounce.run(300) { doSearch() }
 *
 * Java:
 *   private final DebounceUtils debounce = new DebounceUtils();
 *   debounce.run(300, () -> doSearch());
 */
class DebounceUtils {

    private val handler = Handler(Looper.getMainLooper())
    private var pending: Runnable? = null

    /** Debounce with a custom [delayMs]. Cancels any previously scheduled call. */
    fun run(delayMs: Long = 300L, action: () -> Unit) {
        pending?.let { handler.removeCallbacks(it) }
        pending = Runnable { action() }
        handler.postDelayed(pending!!, delayMs)
    }

    /** Java-friendly overload. */
    fun run(delayMs: Long, action: Runnable) = run(delayMs) { action.run() }

    fun run50(action: () -> Unit)   = run(50, action)
    fun run100(action: () -> Unit)  = run(100, action)
    fun run200(action: () -> Unit)  = run(200, action)
    fun run300(action: () -> Unit)  = run(300, action)
    fun run500(action: () -> Unit)  = run(500, action)
    fun run1000(action: () -> Unit) = run(1000, action)

    // Java-friendly fixed-delay overloads
    fun run50(action: Runnable)   = run(50) { action.run() }
    fun run100(action: Runnable)  = run(100) { action.run() }
    fun run200(action: Runnable)  = run(200) { action.run() }
    fun run300(action: Runnable)  = run(300) { action.run() }
    fun run500(action: Runnable)  = run(500) { action.run() }
    fun run1000(action: Runnable) = run(1000) { action.run() }

    /** Cancels any pending callback. */
    fun cancel() {
        pending?.let { handler.removeCallbacks(it) }
        pending = null
    }
}
