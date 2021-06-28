package com.xd.androidutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xihad.myapplication.utils.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidUtils.init(this)

        AndroidUtils.sharePrefSettings.setBoolValue("key", false)

        AndroidUtils.toast(AndroidUtils.sharePrefSettings.getBoolValue("key").toString())

    }
}