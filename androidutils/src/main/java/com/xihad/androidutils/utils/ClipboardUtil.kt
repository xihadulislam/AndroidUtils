package com.xihad.androidutils.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

object ClipboardUtil {

    /** Copies [text] to the clipboard with an optional [label]. */
    fun copyText(context: Context, text: String, label: String = "text") {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    /** Returns the current plain text content of the clipboard, or null if empty. */
    fun pasteText(context: Context): String? {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = cm.primaryClip ?: return null
        if (clip.itemCount == 0) return null
        return clip.getItemAt(0).coerceToText(context)?.toString()
    }

    /** Returns true if the clipboard has plain text content. */
    fun hasText(context: Context): Boolean {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return cm.hasPrimaryClip() &&
                cm.primaryClipDescription?.hasMimeType("text/*") == true
    }

    /** Clears the clipboard. */
    fun clear(context: Context) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText("", ""))
    }
}
