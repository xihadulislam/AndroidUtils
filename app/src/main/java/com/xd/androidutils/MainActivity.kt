package com.xd.androidutils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xihad.androidutils.AndroidUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sharePrefSettings = AndroidUtils.getSharePrefSetting(this);

        sharePrefSettings.setBoolValue("key", false)

        val kBoolean = sharePrefSettings.getBoolValue("key")

        sharePrefSettings.setStringValue("key2", "xihad islam")

        val xd = sharePrefSettings.getStringValue("key2");
        AndroidUtils.toast(this, xd)


//        toast.setOnClickListener {
//            AndroidUtils.toast(this, "show something")
//        }
//
//        showSnack.setOnClickListener {
//            AndroidUtils.getSnackBar(this).snackBar("show something")
//        }
//
//        showSnackSuccess.setOnClickListener {
//            AndroidUtils.getSnackBar(this).successSnack(root, "show something")
//        }
//
//        showSnackInfo.setOnClickListener {
//            AndroidUtils.getSnackBar(this).infoSnack(root, "show something", Gravity.BOTTOM, fun() {
//                AndroidUtils.toast(this, "click")
//            })
//        }
//
//        showSnackWarning.setOnClickListener {
//            AndroidUtils.getSnackBar(this).warningSnack(root, "show something")
//        }
//
//        showSnackError.setOnClickListener {
//            AndroidUtils.getSnackBar(this).errorSnack(root, "show something")
//        }
//
//
//        playTapSound.setOnClickListener {
//            AndroidUtils.getMediaPlayer().playClickSound(this)
//        }
//
//
//        startMediaPlayer.setOnClickListener {
//            //  AndroidUtils.startMediaPlayer()
//        }
//        stopMediaPlayer.setOnClickListener {
//            //  AndroidUtils.stopMediaPlayer()
//        }
//
//        showKeyboard.setOnClickListener {
//            //    AndroidUtils.showKeyboard()
//        }
//
//        startNextActivity.setOnClickListener {
//            AndroidUtils.getIntent().startNextActivity(this, SecondActivity::class.java)
//        }
//
//        afterNextActivity.setOnClickListener {
//            AndroidUtils.getIntent().afterNextActivity(this, 2000, SecondActivity::class.java)
//        }
//
//        startFacebookIntent.setOnClickListener {
//            AndroidUtils.getIntent().startFacebookIntent(this, "url")
//        }
//
//        isInternetAvailable.setOnClickListener {
//            if (AndroidUtils.getAppUtil().isInternetAvailable(this)) {
//                AndroidUtils.toast(this, "Available")
//            }
//        }


    }
}