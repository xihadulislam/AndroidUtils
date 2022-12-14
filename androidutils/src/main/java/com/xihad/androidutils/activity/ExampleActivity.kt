package com.xihad.androidutils.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
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

        val numberUtils: NumberUtils = AndroidUtils.getNumberUtils()
        numberUtils.numberToWords(100)
        numberUtils.numberInBangla("100")


        // or you can use it with Quick Access

        val word = AndroidUtils.numberToWords(100)

        val banglaNumber = AndroidUtils.numberInBangla("12-10-2022")

        val banNumber = AndroidUtils.getDigitBanglaFromEnglish("1234")

        val enNumber = AndroidUtils.getDigitEnglishFromBangla(banNumber)

    }
}