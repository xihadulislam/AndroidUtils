package com.xihad.androidutils.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

object TextUtils {

    fun SpannableString.withClickableSpan(
        clickablePart: String, onClickListener: () -> Unit
    ): SpannableString {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) = onClickListener.invoke()

        }
        val clickablePartStart = indexOf(clickablePart)
        setSpan(
            clickableSpan,
            clickablePartStart,
            clickablePartStart + clickablePart.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun TextView.setColorOfSubstring(substring: String, color: Int) {
        try {
            val spannable = android.text.SpannableString(text)
            val start = text.indexOf(substring)
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, color)),
                start,
                start + substring.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = spannable
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun SpannableStringBuilder.spanText(span: Any): SpannableStringBuilder {
        setSpan(span, 0, length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }

    private fun String.toSpannable() = SpannableStringBuilder(this)

    fun String.foregroundColor(@ColorInt color: Int): SpannableStringBuilder {
        val span = ForegroundColorSpan(color)
        return toSpannable().spanText(span)
    }

    fun String.backgroundColor(@ColorInt color: Int): SpannableStringBuilder {
        val span = BackgroundColorSpan(color)
        return toSpannable().spanText(span)
    }

    fun String.relativeSize(size: Float): SpannableStringBuilder {
        val span = RelativeSizeSpan(size)
        return toSpannable().spanText(span)
    }

    fun String.supserscript(): SpannableStringBuilder {
        val span = SuperscriptSpan()
        return toSpannable().spanText(span)
    }

    fun String.strike(): SpannableStringBuilder {
        val span = StrikethroughSpan()
        return toSpannable().spanText(span)
    }


}