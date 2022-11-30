package com.xihad.androidutils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
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
         * Quick Access methods
         */
        fun uId() = Utils.uId()
        fun isInternetAvailable(context: Context) = AppUtil.isInternetAvailable(context)


        /**
         * ScreenshotUtil
         */
        fun takeScreenshotOfView(view: View, height: Int = view.height, width: Int = view.width) =
            ScreenshotUtil.takeScreenshotOfView(view, height, width)

        fun protectToScreenshot(activity: Activity) = ScreenshotUtil.protectToScreenshot(activity)


        /**
         * Util
         */
        fun postDelayed(milliSecond: Long, func: () -> Unit) = Utils.postDelayed(milliSecond, func)

        fun splitString(str: String, limit: Int) = Utils.splitString(str, limit)

        fun roundOffDecimal(number: Float) = Utils.roundOffDecimal(number)

        fun getJsonFromAsset(context: Context, fileName: String) =
            Utils.getJsonFromAsset(context, fileName)


        /**
         * ApplicationUtil
         */
        fun setWebView(url: String, webView: WebView) = ApplicationUtil.setWebView(url, webView)

        fun getSystemApplications(context: Context) = ApplicationUtil.getSystemApplications(context)

        fun getInstallApplications(context: Context) =
            ApplicationUtil.getInstallApplications(context)

        fun getAllApplications(context: Context) = ApplicationUtil.getAllApplications(context)

        fun isAppOnForeground(context: Context) = ApplicationUtil.isAppOnForeground(context)

        fun isSystemPackage(pkgInfo: PackageInfo) = ApplicationUtil.isSystemPackage(pkgInfo)


        /**
         *  EncryptionUtil
         */
        fun encrypt(value: String) = EncryptionUtil.encrypt(value)
        fun decrypt(value: String) = EncryptionUtil.decrypt(value)



        /**
         * show a Toast in simple way
         */
        fun toast(context: Context, msg: String) =
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()


    }


}

