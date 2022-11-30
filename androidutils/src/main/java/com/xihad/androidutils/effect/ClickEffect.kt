package com.xihad.androidutils.effect

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import java.lang.ref.WeakReference

@SuppressLint("ClickableViewAccessibility")
class ClickEffect(view: View) {


    companion object {
        private const val S_VALUE = 0.93f
        private const val ANIMATION_DURATION = 100L
    }

    init {
        if (!view.hasOnClickListeners()) view.setOnClickListener { }
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> buildShrinkAnimator()?.start()
                MotionEvent.ACTION_UP -> buildGrowAnimator()?.start()
            }
            return@setOnTouchListener false
        }
    }

    private val weakRefView = WeakReference(view)

    private fun buildGrowAnimator(): Animator? {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, S_VALUE, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, S_VALUE, 1f)
        weakRefView.get()?.apply {
            val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
            animator.duration = ANIMATION_DURATION
            return animator
        }
        return null
    }

    private fun buildShrinkAnimator(): Animator? {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, S_VALUE)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, S_VALUE)
        weakRefView.get()?.apply {
            val animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY)
            animator.duration = ANIMATION_DURATION
            return animator
        }
        return null
    }

}

fun View.applyClickEffect(): View {
    return this.apply {
        ClickEffect(this)
    }
}