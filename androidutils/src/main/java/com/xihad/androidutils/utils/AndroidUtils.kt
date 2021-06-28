package com.xihad.myapplication.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.xihad.androidutils.utils.AppUtil
import org.jsoup.Jsoup
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AndroidUtils {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var activity: Activity

        @SuppressLint("StaticFieldLeak")
        lateinit var sharePrefSettings: SharePrefSettings

        fun init(context: Activity) {
            activity = context
            sharePrefSettings = SharePrefSettings.getInstance(activity)!!
        }

        fun snackBar(msg: String) {
            Snackbar.make(activity.window.decorView.rootView, msg, Snackbar.LENGTH_LONG).show()
        }

        fun snackBar(view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
        }

        fun toast(msg: String) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        }

        fun startNextActivity(cls: Class<*>?, isFinish: Boolean = false) {
            if (AppUtil.isOpenRecently()) return
            Intent(activity, cls).also {
                activity.startActivity(it)
            }
            if (isFinish) activity.finish()
        }

        fun afterNextActivity(milliSecond: Long, cls: Class<*>?, isFinish: Boolean = false) {
            Handler(Looper.getMainLooper()).postDelayed({
                Intent(activity, cls).also {
                    activity.startActivity(it)
                }
                if (isFinish) activity.finish()
            }, milliSecond)
        }


        fun startPrivacyActivity(url: String) {
            val uri = Uri.parse(url)
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        fun startPlayStoreActivity(url: String) {
            val uri = Uri.parse(url)
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

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


        fun openFBPageByUrl(pageUrl: String) {
            val facebookIntent = Intent(Intent.ACTION_VIEW)
            facebookIntent.data = Uri.parse(pageUrl)
            activity.startActivity(facebookIntent)
        }


        fun startShareIntent(mag: String) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, mag)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            activity.startActivity(shareIntent)
        }

        fun startFeedbackActivity(mail: String) {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$mail")
            activity.startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }


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


        fun isInternetAvailable(): Boolean {
            val connectivityManager =
                activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var result = false

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
                result = when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    connectivityManager.activeNetworkInfo?.run {
                        result = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }

            return result
        }


        fun loadOfflineImage(imageView: ImageView, image: Int) {
            Glide.with(imageView.context).load(image).placeholder(image).into(imageView)
        }

        fun loadImage(imageView: ImageView, imageLink: String, placeholder: Int) {
            Glide.with(imageView.context)
                .load(imageLink)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(placeholder)
                .placeholder(placeholder)
                .dontTransform()
                .into(imageView)
        }


        fun html2text(html: String): String {
            return try {
                Jsoup.parse(html).body().text()
            } catch (e: Exception) {
                ""
            }
        }

        fun splitString(str: String, limit: Int): String {
            var subString = ""
            if (str.isNotEmpty() && limit > 0) {
                subString = str.substring(0, limit)
            }
            return subString
        }

        fun getCurrentTime(): Long {
            return System.currentTimeMillis()
        }

        fun getCurrentTimeAndDate(): Date {
            return Calendar.getInstance().time
        }

        fun milliSecondToHM(millis: Long): String {
            val hours = TimeUnit.MILLISECONDS.toHours(millis)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
            val seconds =
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)

            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

        fun parseDate(
            dateString: String,
            fromPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ"
        ): Date? {
            return SimpleDateFormat(fromPattern, Locale.getDefault()).parse(dateString)
        }


        private const val TAG = "AndroidUtils"
    }
}