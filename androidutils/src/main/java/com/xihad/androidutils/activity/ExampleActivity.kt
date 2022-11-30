package com.xihad.androidutils.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xihad.androidutils.AndroidUtils
import com.xihad.androidutils.R
import com.xihad.androidutils.utils.DebounceUtils

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        AndroidUtils.getSharePrefSetting(this).getDoubleValue("total")

    }
}