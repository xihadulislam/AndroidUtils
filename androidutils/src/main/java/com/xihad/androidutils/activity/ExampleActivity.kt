package com.xihad.androidutils.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xihad.androidutils.AndroidUtils
import com.xihad.androidutils.R
import com.xihad.androidutils.effect.applyClickEffect
import com.xihad.androidutils.utils.DebounceUtils
import com.xihad.androidutils.utils.NumberUtils.enDigitToBn
import com.xihad.androidutils.utils.PaymentUtils.twoDigitString
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

    }
}