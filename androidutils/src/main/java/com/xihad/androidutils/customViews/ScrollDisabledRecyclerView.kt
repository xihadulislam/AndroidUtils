package com.xihad.androidutils.customViews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.Nullable

class ScrollDisabledRecyclerView : RecyclerView {

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, @Nullable attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, @Nullable attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent): Boolean {
        return e.action == MotionEvent.ACTION_MOVE
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        return false
    }
}