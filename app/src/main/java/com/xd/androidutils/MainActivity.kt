package com.xd.androidutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import com.xihad.androidutils.utils.AndroidUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidUtils.init(this)

        AndroidUtils.sharePrefSettings.setBoolValue("key", false)

        AndroidUtils.toast(AndroidUtils.sharePrefSettings.getBoolValue("key").toString())

        findViewById<Button>(R.id.click).setOnClickListener {
            AndroidUtils.successSnack(root, "dgfdfsg", Gravity.BOTTOM) {
                AndroidUtils.toast("ggggggggggg")
            }

        }

    }
}