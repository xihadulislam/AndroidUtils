package com.xihad.androidutils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.xihad.androidutils.R
import com.xihad.myapplication.utils.SharePrefSettings
import org.jsoup.Jsoup
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class AndroidUtils {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        @SuppressLint("StaticFieldLeak")
        lateinit var sharePrefSettings: SharePrefSettings

        private var mPlayer: MediaPlayer? = null

        private var length: Int = 0


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

        fun toast(msg: String) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        }

        /**
         * Intent a new activity.
         *
         */

        fun startNextActivity(cls: Class<*>?, isFinish: Boolean = false) {
            if (AppUtil.isOpenRecently()) return
            Intent(activity, cls).also {
                activity.startActivity(it)
            }
            if (isFinish) activity.finish()
        }

        /**
         *
         *
         */
        fun afterNextActivity(milliSecond: Long, cls: Class<*>?, isFinish: Boolean = false) {
            Handler(Looper.getMainLooper()).postDelayed({
                Intent(activity, cls).also {
                    activity.startActivity(it)
                }
                if (isFinish) activity.finish()
            }, milliSecond)
        }

        /**
         *
         *
         */
        fun startPrivacyActivity(url: String) {
            val uri = Uri.parse(url)
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        /**
         *
         *
         */
        fun startPlayStoreActivity(url: String) {
            val uri = Uri.parse(url)
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        /**
         *
         *
         */
        fun startRateAppActivity() {
            try {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=${activity.applicationContext.packageName}")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            String.format(
                                "https://play.google.com/store/apps/details?id=",
                                activity.applicationContext.packageName
                            )
                        )
                    )
                )
            }
        }

        /**
         *
         *
         */
        fun startFacebookIntent(link: String) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, link)
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.facebook.katana")
            if (sendIntent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(sendIntent)
            } else {
                toast("Facebook app not found")
            }
        }
        /**
         *
         *
         */
        fun startWhatsAppIntent(link: String) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, link)
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.whatsapp")
            if (sendIntent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(sendIntent)
            } else {
                toast("WhatsApp app not found")
            }
        }
        /**
         *
         *
         */
        fun startTwitterIntent(link: String) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, link)
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.twitter.android")
            if (sendIntent.resolveActivity(activity.packageManager) != null) {
                activity.startActivity(sendIntent)
            } else {
                toast("twitter app not found")
            }
        }

        /**
         *
         *
         */
        fun openFBPageByUrl(pageUrl: String) {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            facebookIntent.data = Uri.parse(pageUrl)
            activity.startActivity(facebookIntent)
        }
        /**
         *
         *
         */
        fun startShareIntent(mag: String) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mag)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            activity.startActivity(shareIntent)
        }
        /**
         *
         *
         */
        fun startFeedbackActivity(mail: String) {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$mail")
            activity.startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }
        /**
         *
         *
         */
        fun getJsonFromAsset(fileName: String): String {
            var jsonString = ""
            try {
                jsonString = activity.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return jsonString
            }
            return jsonString
        }
        /**
         *
         *
         */
        fun isInternetAvailable(): Boolean {
            return AppUtil.isInternetAvailable(activity)
        }

        /**
         *
         *
         */
        fun loadOfflineImage(imageView: ImageView, image: Int) {
            Glide.with(imageView.context).load(image).placeholder(image).into(imageView)
        }
        /**
         *
         *
         */
        fun loadImage(imageView: ImageView, imageLink: String, placeholder: Int) {
            Glide.with(imageView.context)
                .load(imageLink)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(placeholder)
                .placeholder(placeholder)
                .dontTransform()
                .into(imageView)
        }

        /**
         *
         *
         */
        fun getToday(): String {
            val c = Calendar.getInstance().time
            val df = SimpleDateFormat("yyyy_MM_dd", Locale.US)
            return df.format(c)
        }

        /**
         *
         *
         */
        fun roundOffDecimal(number: Float): Float {
            return try {
                val df = DecimalFormat("#.#", DecimalFormatSymbols(Locale.US))
                df.format(number).toFloat()
            } catch (e: Exception) {
                number
            }
        }


        /**
         *
         *
         */
        fun splitString(str: String, limit: Int): String {
            var subString = ""
            if (str.isNotEmpty() && limit > 0) {
                subString = str.substring(0, limit)
            }
            return subString
        }

        /**
         *
         *
         */
        fun getCurrentTime(): Long {
            return System.currentTimeMillis()
        }


        /**
         *
         *
         */
        fun getCurrentTimeAndDate(): Date {
            return Calendar.getInstance().time
        }


        /**
         *
         *
         */
        fun milliSecondToHM(millis: Long): String {
            val hours = TimeUnit.MILLISECONDS.toHours(millis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)

            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }


        /**
         *
         *
         */
        fun parseDate(
            dateString: String,
            fromPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
        ): Date? {
            return SimpleDateFormat(fromPattern, Locale.getDefault()).parse(dateString)
        }


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

        fun snackBar(msg: String) {
            Snackbar.make(activity.window.decorView.rootView, msg, Snackbar.LENGTH_LONG).show()
        }

        fun snackBar(view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
        }

        fun setDrawable(dId: Int): Drawable? {
            return ContextCompat.getDrawable(activity, dId)
        }

        fun setColor(cId: Int): Int {
            return ContextCompat.getColor(activity, cId)
        }


        /**
         *
         *
         */

        fun playTapSound() {
            val am = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val vol = 0.5f
            am.playSoundEffect(AudioManager.FX_KEY_CLICK, vol)
        }

        /**
         *
         *
         */
        fun playClickSound() {
            val am = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val vol = 0.5f
            am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, vol)
        }

        /**
         *  MediaPlayer @xihad
         */

        fun startMediaPlayer(file: Int, isLooping: Boolean = false) {
            if (mPlayer == null) {
                mPlayer = MediaPlayer.create(activity, file)
            }
            mPlayer?.start()
            length = 0
            if (isLooping) {
                mPlayer?.isLooping = true
            } else {
                mPlayer?.setOnCompletionListener {
                    stopMediaPlayer()
                }
            }
        }
        /**
         *
         *
         */
        fun pauseMediaPlayer() {
            mPlayer?.let {
                it.pause()
                length = it.currentPosition
            }
        }

        fun resumeMediaPlayer() {
            mPlayer?.let {
                it.seekTo(length)
                it.start()
            }
        }
        /**
         *
         *
         */
        fun stopMediaPlayer() {
            mPlayer?.let {
                it.release()
                mPlayer = null
            }
        }

        /**
         *
         *
         */
        fun getRandomColor(): Int {
            val list = mutableListOf<Int>()
            list.add(Color.parseColor("#EBFAEE"))
            list.add(Color.parseColor("#FCF5D5"))
            list.add(Color.parseColor("#E3ECFF"))
            list.add(Color.parseColor("#FFD1DA"))
            list.add(Color.parseColor("#F6E6FF"))
            list.shuffle()
            list.shuffle()
            return list[0]
        }

        /**
         *
         *
         */
        fun getRandomColorList(): List<Int> {
            val list: MutableList<Int> = mutableListOf()
            list.add(Color.parseColor("#EBFAEE"))
            list.add(Color.parseColor("#FCF5D5"))
            list.add(Color.parseColor("#E3ECFF"))
            list.add(Color.parseColor("#FFD1DA"))
            list.add(Color.parseColor("#F6E6FF"))
            list.shuffle()
            return list
        }

        fun getRandomInt(until: Int): Int {
            return Random.nextInt(100)
        }

        /**
         *
         *
         */
        fun setStatusBarColor(color: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.window.statusBarColor = ContextCompat.getColor(activity, color)
            }
        }

        /**
         *
         *
         */
        fun showKeyboard() {
            val view = activity.currentFocus
            val methodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

        /**
         *
         *
         */
        fun hideSoftKeyBoard(view: View) {
            try {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         *
         *
         */
        private fun isAppOnForeground(appPackageName: String): Boolean {
            val activityManager =
                activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val appProcesses = activityManager.runningAppProcesses ?: return false
            for (appProcess in appProcesses) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    appProcess.processName == appPackageName
                ) {
                    return true
                }
            }
            return false
        }

        /**
         *
         *
         */

        @SuppressLint("SetJavaScriptEnabled")
        private fun setWebView(response: String, webView: WebView) {

            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return true
                }
            }
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(response)
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                }
            }
        }



        private const val TAG = "AndroidUtils"
    }
}