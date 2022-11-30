package com.xihad.androidutils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.xihad.androidutils.utils.*

class AndroidUtils private constructor() {

    companion object {
        fun init(): AndroidUtils {
            synchronized(this) {
                return AndroidUtils()
            }
        }

        fun getSharePrefSetting(context: Context): SharePrefSettings =
            SharePrefSettings.getInstance(context.applicationContext)!!

        fun getIntent(): IntentUtil = IntentUtil

        fun getSnackBar(activity: Activity): MySnackBar = MySnackBar.init(activity)

        fun getMediaPlayer(): MediaPlayerUtil = MediaPlayerUtil

        fun getColorUtil(): ColorUtil = ColorUtil

        fun getApplicationUtil(): ApplicationUtil = ApplicationUtil

        fun getEncryptionUtil(): EncryptionUtil = EncryptionUtil

        fun getScreenshotUtil(): ScreenshotUtil = ScreenshotUtil

        fun getDeviceInfo(): DeviceInfoUtil = DeviceInfoUtil

        fun getImageUtil(): ImageUtil = ImageUtil

        fun getUtils(): Utils = Utils

        fun getAppUtil(): AppUtil = AppUtil

        fun getTimeUtils(): TimeUtils = TimeUtils

        fun getKeyboardUtil(): KeyboardUtil = KeyboardUtil

        fun getLogUtils(): LogUtils = LogUtils

        fun getDebounceUtils(): DebounceUtils = DebounceUtils


        /**
         * show a Toast in simple way
         */
        fun toast(context: Context, msg: String) =
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()


    }


}

