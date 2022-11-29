package com.xihad.androidutils.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import com.xihad.androidutils.AndroidUtils

object IntentUtil {


    /**
     * Intent a new activity.
     *
     */

    fun startNextActivity(activity: Activity, cls: Class<*>?, isFinish: Boolean = false) {
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
    fun afterNextActivity(
        activity: Activity,
        milliSecond: Long,
        cls: Class<*>?,
        isFinish: Boolean = false
    ) {
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
    fun startPrivacyActivity(activity: Activity, url: String) {
        val uri = Uri.parse(url)
        activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    /**
     *
     *
     */
    fun startPlayStoreActivity(activity: Activity, url: String) {
        val uri = Uri.parse(url)
        activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    /**
     *
     *
     */
    fun startRateAppActivity(activity: Activity) {
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
    fun startFacebookIntent(activity: Activity, link: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.facebook.katana")
        if (sendIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(sendIntent)
        } else {
            AndroidUtils.init(activity).toast("Facebook app not found")
        }
    }

    /**
     *
     *
     */
    fun startWhatsAppIntent(activity: Activity, link: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.whatsapp")
        if (sendIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(sendIntent)
        } else {
            AndroidUtils.init(activity).toast("WhatsApp app not found")
        }
    }


    fun startTwitterIntent(activity: Activity, link: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, link)
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.twitter.android")
        if (sendIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(sendIntent)
        } else {
            AndroidUtils.init(activity).toast("twitter app not found")
        }
    }


    fun openFBPageByUrl(activity: Activity, pageUrl: String) {
        val facebookIntent = Intent(Intent.ACTION_VIEW)
        facebookIntent.data = Uri.parse(pageUrl)
        activity.startActivity(facebookIntent)
    }


    fun startShareIntent(activity: Activity, mag: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, mag)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        activity.startActivity(shareIntent)
    }


    fun startFeedbackActivity(activity: Activity, mail: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$mail")
        activity.startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }

}