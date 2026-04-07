package com.xihad.androidutils.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {

    /**
     * Creates a notification channel (required on API 26+).
     * Safe to call multiple times — does nothing if channel already exists.
     *
     * @param channelId   Unique channel ID
     * @param channelName Human-readable channel name shown in settings
     * @param importance  NotificationManager.IMPORTANCE_* constant
     * @param description Optional channel description
     */
    fun createChannel(
        context: Context,
        channelId: String,
        channelName: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
        description: String = ""
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = description
            }
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    /**
     * Builds and shows a simple notification.
     *
     * @param notificationId Unique ID for this notification (use to update or cancel later)
     * @param channelId      Channel ID (must be created first via [createChannel])
     * @param title          Notification title
     * @param message        Notification body text
     * @param smallIcon      Drawable resource for the small icon (required)
     * @param largeIcon      Optional large bitmap icon
     * @param pendingIntent  Optional action when the notification is tapped
     * @param autoCancel     Dismiss the notification when tapped (default true)
     * @param ongoing        Whether the notification is ongoing (can't be dismissed by user)
     */
    fun show(
        context: Context,
        notificationId: Int,
        channelId: String,
        title: String,
        message: String,
        @DrawableRes smallIcon: Int,
        largeIcon: Bitmap? = null,
        pendingIntent: PendingIntent? = null,
        autoCancel: Boolean = true,
        ongoing: Boolean = false
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(autoCancel)
            .setOngoing(ongoing)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        largeIcon?.let { builder.setLargeIcon(it) }
        pendingIntent?.let { builder.setContentIntent(it) }

        try {
            NotificationManagerCompat.from(context).notify(notificationId, builder.build())
        } catch (e: SecurityException) {
            // POST_NOTIFICATIONS permission not granted (Android 13+)
            e.printStackTrace()
        }
    }

    /**
     * Shows a progress notification (e.g. for downloads or uploads).
     *
     * @param max       Maximum progress value (use 0 for indeterminate)
     * @param progress  Current progress value
     * @param indeterminate Set true for indeterminate spinner
     */
    fun showProgress(
        context: Context,
        notificationId: Int,
        channelId: String,
        title: String,
        message: String,
        @DrawableRes smallIcon: Int,
        max: Int = 100,
        progress: Int = 0,
        indeterminate: Boolean = false
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setProgress(max, progress, indeterminate)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        try {
            NotificationManagerCompat.from(context).notify(notificationId, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    /** Cancels a notification by its ID. */
    fun cancel(context: Context, notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }

    /** Cancels all notifications posted by this app. */
    fun cancelAll(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }

    /** Returns true if notifications are enabled for this app. */
    fun areNotificationsEnabled(context: Context): Boolean =
        NotificationManagerCompat.from(context).areNotificationsEnabled()

    /** Returns true if a specific channel is not blocked by the user (API 26+). */
    fun isChannelEnabled(context: Context, channelId: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = nm.getNotificationChannel(channelId) ?: return false
            return channel.importance != NotificationManager.IMPORTANCE_NONE
        }
        return true
    }
}
