package com.xihad.androidutils.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

object ApplicationUtil {

     fun isSystemPackage(pkgInfo: PackageInfo): Boolean {
        return pkgInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

     fun isAppOnForeground(context: Context): Boolean {
        val activityManager =
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                appProcess.processName == context.applicationContext.packageName
            ) {
                return true
            }
        }
        return false
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun getAllApplications(context: Context): List<ApplicationInfo> {
        return context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun getInstallApplications(context: Context): List<ApplicationInfo> {
        val appsInst: MutableList<ApplicationInfo> = mutableListOf()
        val apps: List<ApplicationInfo> = context.packageManager.getInstalledApplications(0)
        for (app in apps) {
            if (app.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                // It is a system app
                Log.d("tag", "getInstallApplications: ")
            } else {
                // It is installed by the user
                appsInst.add(app)
            }
        }
        return appsInst
    }


    @SuppressLint("QueryPermissionsNeeded")
    fun getSystemApplications(context: Context): List<ApplicationInfo> {
        val appsInst: MutableList<ApplicationInfo> = mutableListOf()
        val apps: List<ApplicationInfo> = context.packageManager.getInstalledApplications(0)
        for (app in apps) {
            if (app.flags and (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP or ApplicationInfo.FLAG_SYSTEM) > 0) {
                // It is a system app
                appsInst.add(app)
            } else {
                // It is installed by the user
                Log.d("tag", "getSystemApplications: ")
            }
        }
        return appsInst
    }


    @SuppressLint("SetJavaScriptEnabled")
     fun setWebView(url: String, webView: WebView) {

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
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




}