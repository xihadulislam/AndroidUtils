package com.xihad.androidutils.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator

object ViewUtil {

    // ── Visibility ────────────────────────────────────────────────────────────

    fun View.visible() { visibility = View.VISIBLE }
    fun View.gone() { visibility = View.GONE }
    fun View.invisible() { visibility = View.INVISIBLE }
    fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }
    infix fun View.visibleIf(condition: Boolean) {
        visibility = if (condition) View.VISIBLE else View.GONE
    }
    infix fun View.goneIf(condition: Boolean) {
        visibility = if (condition) View.GONE else View.VISIBLE
    }

    // ── Enable / Disable ──────────────────────────────────────────────────────

    fun View.enable() { isEnabled = true; alpha = 1.0f }
    fun View.disable() { isEnabled = false; alpha = 0.4f }
    fun View.applyEnabled(enabled: Boolean) { if (enabled) enable() else disable() }

    // ── Size ──────────────────────────────────────────────────────────────────

    fun View.setWidth(width: Int) {
        layoutParams = layoutParams.also { it.width = width }
    }

    fun View.setHeight(height: Int) {
        layoutParams = layoutParams.also { it.height = height }
    }

    fun View.resize(width: Int, height: Int) {
        layoutParams = layoutParams.also { it.width = width; it.height = height }
    }

    // ── Margin (requires ViewGroup.MarginLayoutParams) ────────────────────────

    fun View.setMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
        (layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(left, top, right, bottom)
    }

    // ── Animations ────────────────────────────────────────────────────────────

    fun View.fadeIn(duration: Long = 250L, onEnd: (() -> Unit)? = null) {
        if (visibility == View.VISIBLE && alpha == 1f) { onEnd?.invoke(); return }
        alpha = 0f
        visibility = View.VISIBLE
        animate()
            .alpha(1f)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) { onEnd?.invoke() }
            })
    }

    fun View.fadeOut(duration: Long = 250L, hideMode: Int = View.GONE, onEnd: (() -> Unit)? = null) {
        if (visibility != View.VISIBLE) { onEnd?.invoke(); return }
        animate()
            .alpha(0f)
            .setDuration(duration)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibility = hideMode
                    alpha = 1f
                    onEnd?.invoke()
                }
            })
    }

    fun View.scaleIn(duration: Long = 250L, onEnd: (() -> Unit)? = null) {
        scaleX = 0f; scaleY = 0f; alpha = 0f
        visibility = View.VISIBLE
        animate()
            .scaleX(1f).scaleY(1f).alpha(1f)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) { onEnd?.invoke() }
            })
    }

    fun View.scaleOut(duration: Long = 250L, onEnd: (() -> Unit)? = null) {
        animate()
            .scaleX(0f).scaleY(0f).alpha(0f)
            .setDuration(duration)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibility = View.GONE
                    scaleX = 1f; scaleY = 1f; alpha = 1f
                    onEnd?.invoke()
                }
            })
    }

    fun View.shake(duration: Long = 400L) {
        animate().translationX(16f).setDuration(duration / 6).withEndAction {
            animate().translationX(-16f).setDuration(duration / 6).withEndAction {
                animate().translationX(8f).setDuration(duration / 6).withEndAction {
                    animate().translationX(-8f).setDuration(duration / 6).withEndAction {
                        animate().translationX(0f).setDuration(duration / 6)
                    }
                }
            }
        }
    }
}
