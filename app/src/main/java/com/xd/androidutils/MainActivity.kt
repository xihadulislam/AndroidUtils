package com.xd.androidutils

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.xihad.androidutils.AndroidUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AndroidUtils.init(this)
//
//        AndroidUtils.sharePrefSettings.setBoolValue("key", false)
//
//        val kBoolean = AndroidUtils.sharePrefSettings.getBoolValue("key")
//
//        AndroidUtils.sharePrefSettings.setStringValue("key2", "xihad islam")
//
//        val xd = AndroidUtils.sharePrefSettings.getStringValue("key2");
//        AndroidUtils.toast(xd)
//
//        toast.setOnClickListener {
//            AndroidUtils.toast("show something")
//        }
//        showSnack.setOnClickListener {
//            AndroidUtils.snackBar("show something")
//        }
//
//        showSnackSuccess.setOnClickListener {
//            AndroidUtils.successSnack(root, "show something")
//        }
//
//        showSnackInfo.setOnClickListener {
//            AndroidUtils.infoSnack(root, "show something", Gravity.BOTTOM, fun() {
//                AndroidUtils.toast("click")
//            })
//        }
//
//        showSnackWarning.setOnClickListener {
//            AndroidUtils.warningSnack(root, "show something")
//        }
//
//        showSnackError.setOnClickListener {
//            AndroidUtils.errorSnack(root, "show something")
//        }
//
//
//        playTapSound.setOnClickListener {
//            AndroidUtils.playClickSound()
//        }
//
//
//        startMediaPlayer.setOnClickListener {
//          //  AndroidUtils.startMediaPlayer()
//        }
//        stopMediaPlayer.setOnClickListener {
//          //  AndroidUtils.stopMediaPlayer()
//        }
//
//        showKeyboard.setOnClickListener {
//          AndroidUtils.showKeyboard()
//        }
//
//        startNextActivity.setOnClickListener {
//          AndroidUtils.startNextActivity(SecondActivity::class.java)
//        }
//
//        afterNextActivity.setOnClickListener {
//          AndroidUtils.afterNextActivity(2000,SecondActivity::class.java)
//        }
//
//        startFacebookIntent.setOnClickListener {
//          AndroidUtils.startFacebookIntent("url")
//        }
//
//        isInternetAvailable.setOnClickListener {
//          if (AndroidUtils.isInternetAvailable()){
//              AndroidUtils.toast("Available")
//          }
//        }
//



    }
}