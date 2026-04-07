package com.xihad.androidutils.utils

import android.app.Activity
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.xihad.androidutils.R
import java.lang.ref.WeakReference

class MySnackBar private constructor(activity: Activity) {

    // WeakReference prevents holding the Activity beyond its lifecycle
    private val activityRef = WeakReference(activity)

    companion object {
        fun init(activity: Activity): MySnackBar = MySnackBar(activity)
    }

    fun snackBar(msg: String) {
        val activity = activityRef.get() ?: return
        Snackbar.make(activity.window.decorView.rootView, msg, Snackbar.LENGTH_LONG).show()
    }

    fun snackBar(view: View, msg: String) =
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()

    fun defaultSnack(view: View, msg: String, gravity: Int = Gravity.TOP, icon: Int = R.drawable.ic_hand, func: () -> Unit = {}) =
        show(view, msg, gravity, 0, icon, func)

    fun infoSnack(view: View, msg: String, gravity: Int = Gravity.TOP, icon: Int = R.drawable.ic_info, func: () -> Unit = {}) =
        show(view, msg, gravity, 4, icon, func)

    fun warningSnack(view: View, msg: String, gravity: Int = Gravity.TOP, icon: Int = R.drawable.ic_warning, func: () -> Unit = {}) =
        show(view, msg, gravity, 3, icon, func)

    fun errorSnack(view: View, msg: String, gravity: Int = Gravity.TOP, icon: Int = R.drawable.ic_error, func: () -> Unit = {}) =
        show(view, msg, gravity, 2, icon, func)

    fun successSnack(view: View, msg: String, gravity: Int = Gravity.TOP, icon: Int = R.drawable.ic_task_complete, func: () -> Unit = {}) =
        show(view, msg, gravity, 1, icon, func)

    private fun show(
        view: View,
        msg: String,
        gravity: Int,
        status: Int,
        icon: Int,
        func: () -> Unit
    ) {
        val activity = activityRef.get()
        if (activity == null || activity.isFinishing || activity.isDestroyed) return
        try {
            val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
            val snackView = snackBar.view

            val layoutParams = FrameLayout.LayoutParams(snackView.layoutParams)
            layoutParams.gravity = gravity
            layoutParams.setMargins(0, 30, 0, 0)
            snackView.layoutParams = layoutParams

            val layout = snackView as Snackbar.SnackbarLayout
            val customView = activity.layoutInflater.inflate(R.layout.snack_bar_layout, null)

            snackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

            val rootView  = customView.findViewById<ConstraintLayout>(R.id.snackbar_id)
            val close     = customView.findViewById<ImageView>(R.id.snackBarClose)
            val iconView  = customView.findViewById<ImageView>(R.id.imageView)
            val snackMsg  = customView.findViewById<TextView>(R.id.snackBarText)

            snackMsg.text = msg
            close.setOnClickListener { snackBar.dismiss(); func() }
            iconView.setImageDrawable(ContextCompat.getDrawable(activity, icon))

            val bgRes = when (status) {
                1    -> R.drawable.sn_success
                2    -> R.drawable.sn_error
                3    -> R.drawable.sn_warning
                4    -> R.drawable.sn_info
                else -> R.drawable.sn_default
            }
            rootView.background = ContextCompat.getDrawable(activity, bgRes)

            layout.setPadding(0, 0, 0, 0)
            layout.addView(customView, 0)
            snackBar.show()
        } catch (e: Exception) {
            Log.e("MySnackBar", "show: ${e.message}")
        }
    }
}
