package com.xihad.androidutils.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import java.util.Locale

object TextUtils {

    fun SpannableString.withClickableSpan(
        clickablePart: String,
        onClickListener: () -> Unit
    ): SpannableString {
        val start = indexOf(clickablePart)
        if (start < 0) return this
        setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) = onClickListener()
            },
            start,
            start + clickablePart.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun TextView.setColorOfSubstring(substring: String, @ColorInt color: Int) {
        try {
            val spannable = SpannableString(text)
            val start = text.indexOf(substring)
            if (start < 0) return
            spannable.setSpan(
                ForegroundColorSpan(color),
                start,
                start + substring.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = spannable
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun TextView.setColorOfSubstringRes(substring: String, colorRes: Int) {
        setColorOfSubstring(substring, ContextCompat.getColor(context, colorRes))
    }

    private fun String.toSpannable() = SpannableStringBuilder(this)

    private fun SpannableStringBuilder.spanText(span: Any): SpannableStringBuilder {
        setSpan(span, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    fun String.foregroundColor(@ColorInt color: Int): SpannableStringBuilder =
        toSpannable().spanText(ForegroundColorSpan(color))

    fun String.backgroundColor(@ColorInt color: Int): SpannableStringBuilder =
        toSpannable().spanText(BackgroundColorSpan(color))

    fun String.relativeSize(size: Float): SpannableStringBuilder =
        toSpannable().spanText(RelativeSizeSpan(size))

    fun String.superscript(): SpannableStringBuilder =
        toSpannable().spanText(SuperscriptSpan())

    fun String.subscript(): SpannableStringBuilder =
        toSpannable().spanText(SubscriptSpan())

    fun String.strike(): SpannableStringBuilder =
        toSpannable().spanText(StrikethroughSpan())

    fun String.bold(): SpannableStringBuilder =
        toSpannable().spanText(StyleSpan(android.graphics.Typeface.BOLD))

    fun String.italic(): SpannableStringBuilder =
        toSpannable().spanText(StyleSpan(android.graphics.Typeface.ITALIC))

    fun String.underline(): SpannableStringBuilder =
        toSpannable().spanText(UnderlineSpan())

    fun MutableList<String>.concatenateLowercase(): String =
        joinToString("") { it.lowercase(Locale.ROOT) }

    /** Returns true if the string contains only whitespace or is empty. */
    fun String.isBlankOrEmpty(): Boolean = isBlank()

    /** Truncates a string to [maxLength] and appends [ellipsis] if it exceeds the limit. */
    fun String.truncate(maxLength: Int, ellipsis: String = "…"): String =
        if (length <= maxLength) this else take(maxLength) + ellipsis

    /** Removes all extra whitespace, collapsing multiple spaces into one. */
    fun String.collapseSpaces(): String = trim().replace(Regex("\\s+"), " ")

    /** Returns the string repeated [n] times. */
    fun String.repeatStr(n: Int): String = repeat(n)
}
