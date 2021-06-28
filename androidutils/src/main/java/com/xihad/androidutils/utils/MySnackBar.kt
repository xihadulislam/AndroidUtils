package com.xihad.androidutils.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.xihad.androidutils.R


object MySnackBar {

    fun showSnack(
        activity: Activity,
        view: View,
        msg: String,
        gravity: Int,
        status: Int = 0,
        icon: Int = R.drawable.ic_info,
        func: () -> Unit = {}
    ) {

        try {

            val customSnackBar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)
            val view = customSnackBar.view
            val layoutParams = FrameLayout.LayoutParams(customSnackBar.view.layoutParams)
            layoutParams.gravity = gravity
            layoutParams.setMargins(0, 30, 0, 0)
            view.layoutParams = layoutParams

            val layout = customSnackBar.view as Snackbar.SnackbarLayout
            val customSnackView = activity.layoutInflater.inflate(R.layout.snack_bar_layout, null)

            customSnackBar.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

            val rootView = customSnackView.findViewById(R.id.snackbar_id) as ConstraintLayout
            val close = customSnackView.findViewById(R.id.snackBarClose) as ImageView
            val iconView = customSnackView.findViewById(R.id.imageView) as ImageView
            val snackMsg = customSnackView.findViewById(R.id.snackBarText) as TextView

            snackMsg.text = msg
            close.setOnClickListener {
                customSnackBar.dismiss()
                func()
            }

            iconView.setImageDrawable(ContextCompat.getDrawable(activity, icon))

            when (status) {
                1 -> {
                    rootView.background = ContextCompat.getDrawable(activity, R.drawable.sn_success)
                }
                2 -> {
                    rootView.background = ContextCompat.getDrawable(activity, R.drawable.sn_error)
                }
                3 -> {
                    rootView.background = ContextCompat.getDrawable(activity, R.drawable.sn_warning)
                }
                4 -> {
                    rootView.background = ContextCompat.getDrawable(activity, R.drawable.sn_info)
                }
                else -> {
                    rootView.background = ContextCompat.getDrawable(activity, R.drawable.sn_default)
                }
            }

            // We can also customize the above controls
            layout.setPadding(0, 0, 0, 0)
            layout.addView(customSnackView, 0)

            customSnackBar.show()


        } catch (e: Exception) {
            Log.d("TAG", "showSnack: ")
        }

    }

}