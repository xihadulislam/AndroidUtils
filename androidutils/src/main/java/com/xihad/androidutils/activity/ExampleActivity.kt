package com.xihad.androidutils.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xihad.androidutils.AndroidUtils
import com.xihad.androidutils.R
import kotlinx.android.synthetic.main.snack_bar_layout.*

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        AndroidUtils.getUtils().applyClickEffect(this.imageView)

    }
}