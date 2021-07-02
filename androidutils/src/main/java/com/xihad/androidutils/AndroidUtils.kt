package com.xihad.androidutils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.Gravity
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.xihad.androidutils.utils.*
import java.text.SimpleDateFormat
import java.util.*


class AndroidUtils {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        @SuppressLint("StaticFieldLeak")
        lateinit var sharePrefSettings: SharePrefSettings


        /**
         *  you have must init this method
         *
         */

        fun init(context: Activity) {
            activity = context
            sharePrefSettings = SharePrefSettings.getInstance(activity)!!
        }

        /**
         * show a Toast in simple way
         */

        fun toast(msg: String) = Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()

        /**
         * Intent a new activity.
         */
        fun startNextActivity(cls: Class<*>?, isFinish: Boolean = false) =
            IntentUtil.startNextActivity(activity, cls, isFinish)

        fun afterNextActivity(milliSecond: Long, cls: Class<*>?, isFinish: Boolean = false) =
            IntentUtil.afterNextActivity(activity, milliSecond, cls, isFinish)

        fun startPrivacyActivity(url: String) = IntentUtil.startPrivacyActivity(activity, url)

        fun startPlayStoreActivity(url: String) = IntentUtil.startPlayStoreActivity(activity, url)

        fun startRateAppActivity() = IntentUtil.startRateAppActivity(activity)

        fun startFacebookIntent(link: String) = IntentUtil.startFacebookIntent(activity, link)

        fun startWhatsAppIntent(link: String) = IntentUtil.startWhatsAppIntent(activity, link)

        fun startTwitterIntent(link: String) = IntentUtil.startTwitterIntent(activity, link)

        fun openFBPageByUrl(pageUrl: String) = IntentUtil.openFBPageByUrl(activity, pageUrl)

        fun startShareIntent(mag: String) = IntentUtil.startShareIntent(activity, mag)

        fun startFeedbackActivity(mail: String) = IntentUtil.startFeedbackActivity(activity, mail)

        fun getJsonFromAsset(fileName: String): String = Utils.getJsonFromAsset(activity, fileName)

        fun isInternetAvailable(): Boolean = AppUtil.isInternetAvailable(activity)

        fun loadOfflineImage(imageView: ImageView, image: Int) =
            ImageUtil.loadOfflineImage(imageView, image)

        fun loadImage(imageView: ImageView, imageLink: String, placeholder: Int) =
            ImageUtil.loadImage(imageView, imageLink, placeholder)

        fun getToday(): String = Utils.getToday()

        fun roundOffDecimal(number: Float): Float = Utils.roundOffDecimal(number)

        fun splitString(str: String, limit: Int): String = Utils.splitString(str, limit)

        fun getCurrentTime(): Long = System.currentTimeMillis()

        fun getCurrentTimeAndDate(): Date = Calendar.getInstance().time

        fun milliSecondToHM(millis: Long): String = Utils.milliSecondToHM(millis)

        fun parseDate(
            dateString: String,
            fromPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
        ): Date? = SimpleDateFormat(fromPattern, Locale.getDefault()).parse(dateString)


        fun setDrawable(dId: Int): Drawable? = ContextCompat.getDrawable(activity, dId)

        fun setColor(cId: Int): Int = ContextCompat.getColor(activity, cId)

        fun playTapSound() = MediaPlayerUtil.playTapSound(activity)

        fun playClickSound() = MediaPlayerUtil.playClickSound(activity)

        fun startMediaPlayer(file: Int, isLooping: Boolean = false) =
            MediaPlayerUtil.startMediaPlayer(activity, file, isLooping)

        fun pauseMediaPlayer() = MediaPlayerUtil.pauseMediaPlayer()

        fun resumeMediaPlayer() = MediaPlayerUtil.resumeMediaPlayer()

        fun stopMediaPlayer() = MediaPlayerUtil.stopMediaPlayer()

        fun getRandomColor(): Int = ColorUtil.getRandomColor()

        fun getRandomColorList(): List<Int> = ColorUtil.getRandomColorList()

        fun setStatusBarColor(color: Int) = ColorUtil.setStatusBarColor(activity, color)

        fun showKeyboard(view: View? = activity.currentFocus) =
            KeyboardUtil.showKeyboard(activity, view)

        fun hideSoftKeyBoard() = KeyboardUtil.hideSoftKeyBoard(activity)

        fun isAppOnForeground(): Boolean = ApplicationUtil.isAppOnForeground(activity)

        fun setWebView(response: String, webView: WebView) =
            ApplicationUtil.setWebView(response, webView)

        fun isSystemPackage(pkgInfo: PackageInfo): Boolean =
            ApplicationUtil.isSystemPackage(pkgInfo)

        fun getAllApplications(): List<ApplicationInfo> =
            ApplicationUtil.getAllApplications(activity)

        fun getInstallApplications(): List<ApplicationInfo> =
            ApplicationUtil.getInstallApplications(activity)

        fun getSystemApplications(): List<ApplicationInfo> =
            ApplicationUtil.getSystemApplications(activity)

        fun postDelayed(milliSecond: Long, func: () -> Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                func()
            }, milliSecond)
        }

        fun imageToBase64(image: ImageView): String = ImageUtil.imageToBase64(image)

        fun byteArrayToBase64(byteArray: ByteArray): String =
            Base64.encodeToString(byteArray, Base64.NO_WRAP)

        fun imageToBitmap(image: ImageView): ByteArray = ImageUtil.imageToBitmap(image)

        fun encrypt(value: String): String? = EncryptionUtil.encrypt(value)

        fun decrypt(value: String): String? = EncryptionUtil.decrypt(value)

        fun takeScreenshotOfView(
            view: View, height: Int = view.height,
            width: Int = view.width
        ): Bitmap = ScreenshotUtil.takeScreenshotOfView(view, height, width)

        fun protectToScreenshot() = ScreenshotUtil.protectToScreenshot(activity)

        @SuppressLint("HardwareIds")
        fun getDeviceId(): String = DeviceInfoUtil.getDeviceId(activity)

        fun getDeviceSuperInfo(): String = DeviceInfoUtil.getDeviceSuperInfo()

        fun getDeviceName(): String = DeviceInfoUtil.getDeviceName()

        /**
         * snack bar
         *
         */
        fun defaultSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            icon: Int = R.drawable.ic_hand,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 0, icon, func)
        }

        fun defaultSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 0, R.drawable.ic_hand, func)
        }

        fun defaultSnack(
            view: View,
            msg: String,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, Gravity.TOP, 0, R.drawable.ic_hand, func)
        }

        fun infoSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 4, R.drawable.ic_info, func)
        }

        fun infoSnack(
            view: View,
            msg: String,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, Gravity.TOP, 4, R.drawable.ic_info, func)
        }

        fun infoSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            icon: Int = R.drawable.ic_info,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 4, icon, func)
        }

        fun warningSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            icon: Int = R.drawable.ic_warning,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 3, icon, func)
        }

        fun warningSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 3, R.drawable.ic_warning, func)
        }

        fun warningSnack(
            view: View,
            msg: String,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, Gravity.TOP, 3, R.drawable.ic_warning, func)
        }


        fun errorSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            icon: Int = R.drawable.ic_error,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 2, icon, func)
        }

        fun errorSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 2, R.drawable.ic_error, func)
        }

        fun errorSnack(
            view: View,
            msg: String,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, Gravity.TOP, 2, R.drawable.ic_error, func)
        }


        fun successSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            icon: Int = R.drawable.ic_task_complete,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 1, icon, func)
        }

        fun successSnack(
            view: View,
            msg: String,
            gravity: Int = Gravity.TOP,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(activity, view, msg, gravity, 1, R.drawable.ic_task_complete, func)
        }

        fun successSnack(
            view: View,
            msg: String,
            func: () -> Unit = {}
        ) {
            MySnackBar.showSnack(
                activity,
                view,
                msg,
                Gravity.TOP,
                1,
                R.drawable.ic_task_complete,
                func
            )
        }

        fun snackBar(msg: String) =
            Snackbar.make(activity.window.decorView.rootView, msg, Snackbar.LENGTH_LONG).show()

        fun snackBar(view: View, msg: String) =
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()

        private const val TAG = "AndroidUtils"
    }
}