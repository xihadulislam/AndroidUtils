package com.xihad.androidutils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.xihad.androidutils.utils.*

class AndroidUtils private constructor(
    private val sharePrefSettings: SharePrefSettings,
    private var context: Context
) {

    companion object {
        fun init(context: Context): AndroidUtils {
            synchronized(this) {
                val sPref = SharePrefSettings.getInstance(context.applicationContext)!!
                return AndroidUtils(sPref, context)
            }
        }
    }

    fun getSharePrefSetting(): SharePrefSettings = sharePrefSettings

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
    fun toast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()


}

