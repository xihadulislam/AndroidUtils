package com.xihad.androidutils.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build

object AppInfoUtil {

    /** Returns the app's version name (e.g. "3.1.0"). */
    fun getVersionName(context: Context): String = try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "unknown"
    } catch (e: PackageManager.NameNotFoundException) { "unknown" }

    /** Returns the app's version code. */
    fun getVersionCode(context: Context): Long = try {
        val info = context.packageManager.getPackageInfo(context.packageName, 0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) info.longVersionCode
        else @Suppress("DEPRECATION") info.versionCode.toLong()
    } catch (e: PackageManager.NameNotFoundException) { -1L }

    /** Returns the app's package name. */
    fun getPackageName(context: Context): String = context.packageName

    /** Returns true if the app is running in debug mode. */
    fun isDebug(context: Context): Boolean = try {
        val flags = context.packageManager.getApplicationInfo(context.packageName, 0).flags
        (flags and android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE) != 0
    } catch (e: PackageManager.NameNotFoundException) { false }

    /** Returns true if an app with [packageName] is installed on the device. */
    fun isAppInstalled(context: Context, packageName: String): Boolean = try {
        context.packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) { false }

    /** Opens the app's page in the Play Store. */
    fun openPlayStore(context: Context, packageName: String = context.packageName) {
        try {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } catch (e: Exception) {
            context.startActivity(
                Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    /** Opens the app's system settings page. */
    fun openAppSettings(context: Context) {
        context.startActivity(
            Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:${context.packageName}"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    /** Launches another installed app by [packageName]. Returns false if not installed. */
    fun launchApp(context: Context, packageName: String): Boolean {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName) ?: return false
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        return true
    }

    /** Returns the installer package name (e.g. "com.android.vending" for Play Store). */
    fun getInstallerPackage(context: Context): String? = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.packageManager.getInstallSourceInfo(context.packageName).installingPackageName
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getInstallerPackageName(context.packageName)
        }
    } catch (e: Exception) { null }

    /** Returns true if the app was installed from the Play Store. */
    fun isInstalledFromPlayStore(context: Context): Boolean =
        getInstallerPackage(context) == "com.android.vending"
}
