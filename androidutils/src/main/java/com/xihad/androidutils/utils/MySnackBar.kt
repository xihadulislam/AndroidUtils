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


class MySnackBar private constructor(private var activity: Activity) {


    companion object {

        fun init(activity: Activity): MySnackBar {
            synchronized(this) {
                return MySnackBar(activity)
            }
        }
    }


    fun snackBar(msg: String) =
        Snackbar.make(activity.window.decorView.rootView, msg, Snackbar.LENGTH_LONG).show()

    fun snackBar(view: View, msg: String) =
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()


    /**
     * snack bar
     *
     */
    fun defaultSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        icon: Int = R.drawable.ic_hand,
        func: () -> Unit = {}
    ) = showSnack(activity, view, msg, gravity, 0, icon, func)


    fun defaultSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            gravity,
            0,
            R.drawable.ic_hand,
            func
        )


    fun defaultSnack(
        view: View,
        msg: String,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            Gravity.TOP,
            0,
            R.drawable.ic_hand,
            func
        )


    fun infoSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            gravity,
            4,
            R.drawable.ic_info,
            func
        )


    fun infoSnack(
        view: View,
        msg: String,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            Gravity.TOP,
            4,
            R.drawable.ic_info,
            func
        )


    fun infoSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        icon: Int = R.drawable.ic_info,
        func: () -> Unit = {}
    ) =
        showSnack(activity, view, msg, gravity, 4, icon, func)


    fun warningSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        icon: Int = R.drawable.ic_warning,
        func: () -> Unit = {}
    ) =
        showSnack(activity, view, msg, gravity, 3, icon, func)


    fun warningSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            gravity,
            3,
            R.drawable.ic_warning,
            func
        )


    fun warningSnack(
        view: View,
        msg: String,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            Gravity.TOP,
            3,
            R.drawable.ic_warning,
            func
        )


    fun errorSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        icon: Int = R.drawable.ic_error,
        func: () -> Unit = {}
    ) =
        showSnack(activity, view, msg, gravity, 2, icon, func)


    fun errorSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            gravity,
            2,
            R.drawable.ic_error,
            func
        )


    fun errorSnack(
        view: View,
        msg: String,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            Gravity.TOP,
            2,
            R.drawable.ic_error,
            func
        )


    fun successSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        icon: Int = R.drawable.ic_task_complete,
        func: () -> Unit = {}
    ) =
        showSnack(activity, view, msg, gravity, 1, icon, func)


    fun successSnack(
        view: View,
        msg: String,
        gravity: Int = Gravity.TOP,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            gravity,
            1,
            R.drawable.ic_task_complete,
            func
        )


    fun successSnack(
        view: View,
        msg: String,
        func: () -> Unit = {}
    ) =
        showSnack(
            activity,
            view,
            msg,
            Gravity.TOP,
            1,
            R.drawable.ic_task_complete,
            func
        )


    private fun showSnack(
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