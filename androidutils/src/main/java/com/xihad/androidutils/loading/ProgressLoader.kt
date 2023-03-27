package com.xihad.androidutils.loading

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.xihad.androidutils.R
import java.util.*

object ProgressLoader {

    private var paymentProgressLoader: ProgressDialog? = null

    fun Context.progressDialogShow() {
        paymentProgressLoader = ProgressDialog(this).apply {
            Objects.requireNonNull<Window>(this.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.isIndeterminate = true
            this.setCancelable(false)
            this.show()
            this.setContentView(R.layout.my_progress)
        }
    }

    private fun progressDialogClose() {
        paymentProgressLoader?.dismiss()
    }


}