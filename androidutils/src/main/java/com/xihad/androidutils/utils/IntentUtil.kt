package com.xihad.androidutils.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.ref.WeakReference

object IntentUtil {

    fun startNextActivity(activity: Activity, cls: Class<*>?, isFinish: Boolean = false) {
        if (AppUtil.isOpenRecently()) return
        activity.startActivity(Intent(activity, cls))
        if (isFinish) activity.finish()
    }

    /**
     * Navigates to [cls] after [milliSecond] delay.
     * Uses WeakReference so the Activity can be GC'd if it finishes before the delay expires.
     */
    fun afterNextActivity(
        activity: Activity,
        milliSecond: Long,
        cls: Class<*>?,
        isFinish: Boolean = false
    ) {
        val ref = WeakReference(activity)
        Handler(Looper.getMainLooper()).postDelayed({
            val a = ref.get()
            if (a != null && !a.isFinishing && !a.isDestroyed) {
                a.startActivity(Intent(a, cls))
                if (isFinish) a.finish()
            }
        }, milliSecond)
    }

    fun startPrivacyActivity(activity: Activity, url: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun startPlayStoreActivity(activity: Activity, url: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun startRateAppActivity(activity: Activity) {
        val pkg = activity.applicationContext.packageName
        try {
            activity.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
            )
        } catch (e: ActivityNotFoundException) {
            activity.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pkg"))
            )
        }
    }

    fun startFacebookIntent(activity: Activity, link: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            setPackage("com.facebook.katana")
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        } else {
            Toast.makeText(activity, "Facebook app not found", Toast.LENGTH_SHORT).show()
        }
    }

    fun startWhatsAppIntent(activity: Activity, link: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            setPackage("com.whatsapp")
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        } else {
            Toast.makeText(activity, "WhatsApp not found", Toast.LENGTH_SHORT).show()
        }
    }

    fun startTwitterIntent(activity: Activity, link: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
            setPackage("com.twitter.android")
        }
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        } else {
            Toast.makeText(activity, "Twitter app not found", Toast.LENGTH_SHORT).show()
        }
    }

    fun openFBPageByUrl(activity: Activity, pageUrl: String) {
        activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(pageUrl)))
    }

    fun startShareIntent(activity: Activity, msg: String) {
        val shareIntent = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, msg)
            type = "text/plain"
        }, null)
        activity.startActivity(shareIntent)
    }

    fun startFeedbackActivity(activity: Activity, mail: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply { data = Uri.parse("mailto:$mail") }
        activity.startActivity(Intent.createChooser(intent, "Send Email"))
    }
}
