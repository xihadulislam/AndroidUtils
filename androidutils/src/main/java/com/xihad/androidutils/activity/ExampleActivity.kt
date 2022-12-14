package com.xihad.androidutils.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.xihad.androidutils.AndroidUtils
import com.xihad.androidutils.R
import com.xihad.androidutils.utils.NumberUtils
import kotlinx.android.synthetic.main.snack_bar_layout.*

class ExampleActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ExampleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)


    }
}