package com.xihad.androidutils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.xihad.androidutils.utils.*
import com.xihad.androidutils.utils.ApplicationUtil.getAllApplications
import com.xihad.androidutils.utils.ApplicationUtil.getInstallApplications
import com.xihad.androidutils.utils.ApplicationUtil.getSystemApplications
import com.xihad.androidutils.utils.ApplicationUtil.isAppOnForeground
import com.xihad.androidutils.utils.EncryptionUtil.decrypt
import com.xihad.androidutils.utils.EncryptionUtil.encrypt
import com.xihad.androidutils.view.ViewUtil

class AndroidUtils private constructor() {

    companion object {

        /** Creates an instance. Also usable from Java: AndroidUtils.init() */
        @JvmStatic
        fun init(): AndroidUtils = synchronized(this) { AndroidUtils() }

        // ── Accessors ─────────────────────────────────────────────────────────

        @JvmStatic
        fun getSharePrefSetting(context: Context): SharePrefSettings =
            SharePrefSettings.getInstance(context.applicationContext)

        @JvmStatic
        fun getIntent(): IntentUtil = IntentUtil

        @JvmStatic
        fun getSnackBar(activity: Activity): MySnackBar = MySnackBar.init(activity)

        @JvmStatic
        fun getMediaPlayer(): MediaPlayerUtil = MediaPlayerUtil

        @JvmStatic
        fun getColorUtil(): ColorUtil = ColorUtil

        @JvmStatic
        fun getApplicationUtil(): ApplicationUtil = ApplicationUtil

        @JvmStatic
        fun getEncryptionUtil(): EncryptionUtil = EncryptionUtil

        @JvmStatic
        fun getScreenshotUtil(): ScreenshotUtil = ScreenshotUtil

        @JvmStatic
        fun getDeviceInfo(): DeviceInfoUtil = DeviceInfoUtil

        @JvmStatic
        fun getImageUtil(): ImageUtil = ImageUtil

        @JvmStatic
        fun getUtils(): Utils = Utils

        @JvmStatic
        fun getAppUtil(): AppUtil = AppUtil

        @JvmStatic
        fun getTimeUtils(): TimeUtils = TimeUtils

        @JvmStatic
        fun getKeyboardUtil(): KeyboardUtil = KeyboardUtil

        @JvmStatic
        fun getLogUtils(): LogUtils = LogUtils

        /** Returns a new DebounceUtils instance. Each caller should hold their own. */
        @JvmStatic
        fun newDebounce(): DebounceUtils = DebounceUtils()

        @JvmStatic
        fun getPaymentUtils(): PaymentUtils = PaymentUtils

        @JvmStatic
        fun getNumberUtils(): NumberUtils = NumberUtils

        @JvmStatic
        fun getTextUtils(): TextUtils = TextUtils

        @JvmStatic
        fun getViewUtil(): ViewUtil = ViewUtil

        @JvmStatic
        fun getClipboardUtil(): ClipboardUtil = ClipboardUtil

        @JvmStatic
        fun getValidationUtil(): ValidationUtil = ValidationUtil

        @JvmStatic
        fun getBitmapUtil(): BitmapUtil = BitmapUtil

        @JvmStatic
        fun getAppInfoUtil(): AppInfoUtil = AppInfoUtil

        @JvmStatic
        fun getStatusBarUtil(): StatusBarUtil = StatusBarUtil

        @JvmStatic
        fun getStringUtil(): StringUtil = StringUtil

        @JvmStatic
        fun getNotificationUtil(): NotificationUtil = NotificationUtil

        @JvmStatic
        fun getStorageUtil(): StorageUtil = StorageUtil

        // ── Toast ─────────────────────────────────────────────────────────────

        @JvmStatic
        fun toast(context: Context, msg: String) =
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        // ── Quick Access: Utils ───────────────────────────────────────────────

        @JvmStatic
        fun uId() = Utils.uId()

        @JvmStatic
        fun postDelayed(milliSecond: Long, func: Runnable) =
            Utils.postDelayed(milliSecond) { func.run() }

        @JvmStatic
        fun splitString(str: String, limit: Int) = Utils.splitString(str, limit)

        @JvmStatic
        fun roundOffDecimal(number: Float) = Utils.roundOffDecimal(number)

        @JvmStatic
        fun getJsonFromAsset(context: Context, fileName: String) =
            Utils.getJsonFromAsset(context, fileName)

        @JvmStatic
        fun validateEmailAddress(email: String?) = Utils.validateEmailAddress(email)

        @JvmStatic
        fun applyClickEffect(view: View) = Utils.applyClickEffect(view)

        // ── Quick Access: Network ─────────────────────────────────────────────

        @JvmStatic
        fun isInternetAvailable(context: Context) = AppUtil.isInternetAvailable(context)

        // ── Quick Access: Screenshot ──────────────────────────────────────────

        @JvmStatic
        fun takeScreenshotOfView(view: View, height: Int, width: Int) =
            ScreenshotUtil.takeScreenshotOfView(view, height, width)

        @JvmStatic
        fun takeScreenshotOfView(view: View) =
            ScreenshotUtil.takeScreenshotOfView(view, view.height, view.width)

        @JvmStatic
        fun protectToScreenshot(activity: Activity) =
            ScreenshotUtil.protectToScreenshot(activity)

        // ── Quick Access: ApplicationUtil ─────────────────────────────────────

        @JvmStatic
        fun setWebView(url: String, webView: WebView) =
            ApplicationUtil.setWebView(url, webView)

        @JvmStatic
        fun getSystemApplications(context: Context) = context.getSystemApplications()

        @JvmStatic
        fun getInstallApplications(context: Context) = context.getInstallApplications()

        @JvmStatic
        fun getAllApplications(context: Context) = context.getAllApplications()

        @JvmStatic
        fun isAppOnForeground(context: Context) = context.isAppOnForeground()

        @JvmStatic
        fun isSystemPackage(pkgInfo: PackageInfo) = ApplicationUtil.isSystemPackage(pkgInfo)

        // ── Quick Access: Encryption ──────────────────────────────────────────

        @JvmStatic
        fun encrypt(value: String) = value.encrypt()

        @JvmStatic
        fun decrypt(value: String) = value.decrypt()

        // ── Quick Access: Payment ─────────────────────────────────────────────

        @JvmStatic
        fun getIncludingTax(total: Double, tax: Double) = PaymentUtils.getIncludingTax(total, tax)

        @JvmStatic
        fun getExcludingTax(total: Double, tax: Double) = PaymentUtils.getExcludingTax(total, tax)

        @JvmStatic
        fun twoDigitDouble(value: Double) = PaymentUtils.twoDigitDouble(value)

        @JvmStatic
        fun twoDigitString(value: Double) = PaymentUtils.twoDigitString(value)

        @JvmStatic
        fun stringToNumber(inputNumber: String) = PaymentUtils.stringToNumber(inputNumber)

        // ── Quick Access: Currency ────────────────────────────────────────────

        @JvmStatic
        fun getCurrencyCode(countryCode: String) = CurrencyUtils.getCurrencyCode(countryCode)

        @JvmStatic
        fun getCurrencySymbol(countryCode: String) = CurrencyUtils.getCurrencySymbol(countryCode)

        @JvmStatic
        fun getCountryCode(countryName: String) = CurrencyUtils.getCountryCode(countryName)

        // ── Quick Access: Number ──────────────────────────────────────────────

        @JvmStatic
        fun numberToWords(number: Long) = NumberUtils.numberToWords(number)

        @JvmStatic
        fun numberInBangla(number: String) = NumberUtils.numberInBangla(number)

        @JvmStatic
        fun getDigitBanglaFromEnglish(number: String) =
            NumberUtils.getDigitBanglaFromEnglish(number)

        @JvmStatic
        fun getDigitEnglishFromBangla(number: String) =
            NumberUtils.getDigitEnglishFromBangla(number)

        // ── Quick Access: Clipboard ───────────────────────────────────────────

        @JvmStatic
        fun copyToClipboard(context: Context, text: String) =
            ClipboardUtil.copyText(context, text)

        @JvmStatic
        fun copyToClipboard(context: Context, text: String, label: String) =
            ClipboardUtil.copyText(context, text, label)

        @JvmStatic
        fun pasteFromClipboard(context: Context): String? = ClipboardUtil.pasteText(context)

        @JvmStatic
        fun clearClipboard(context: Context) = ClipboardUtil.clear(context)

        // ── Quick Access: Validation ──────────────────────────────────────────

        @JvmStatic
        fun isValidEmail(email: String) = ValidationUtil.isValidEmail(email)

        @JvmStatic
        fun isValidPhone(phone: String) = ValidationUtil.isValidPhone(phone)

        @JvmStatic
        fun isValidUrl(url: String) = ValidationUtil.isValidUrl(url)

        @JvmStatic
        fun isStrongPassword(password: String) = ValidationUtil.isStrongPassword(password)

        @JvmStatic
        fun getPasswordStrength(password: String) = ValidationUtil.getPasswordStrength(password)

        @JvmStatic
        fun isValidCreditCard(number: String) = ValidationUtil.isValidCreditCard(number)

        // ── Quick Access: AppInfo ─────────────────────────────────────────────

        @JvmStatic
        fun getVersionName(context: Context) = AppInfoUtil.getVersionName(context)

        @JvmStatic
        fun getVersionCode(context: Context) = AppInfoUtil.getVersionCode(context)

        @JvmStatic
        fun isDebugBuild(context: Context) = AppInfoUtil.isDebug(context)

        @JvmStatic
        fun isAppInstalled(context: Context, packageName: String) =
            AppInfoUtil.isAppInstalled(context, packageName)

        @JvmStatic
        fun openPlayStore(context: Context) = AppInfoUtil.openPlayStore(context)

        @JvmStatic
        fun openAppSettings(context: Context) = AppInfoUtil.openAppSettings(context)

        // ── Quick Access: Storage ─────────────────────────────────────────────

        @JvmStatic
        fun getCacheSize(context: Context) = StorageUtil.getCacheSize(context)

        @JvmStatic
        fun clearCache(context: Context) = StorageUtil.clearCache(context)

        @JvmStatic
        fun getFreeInternalStorage() = StorageUtil.getFreeInternalStorage()

        @JvmStatic
        fun formatStorageSize(bytes: Long) = StorageUtil.formatSize(bytes)

        // ── Quick Access: Notification ────────────────────────────────────────

        @JvmStatic
        fun createNotificationChannel(
            context: Context, channelId: String, channelName: String
        ) = NotificationUtil.createChannel(context, channelId, channelName)

        @JvmStatic
        fun showNotification(
            context: Context,
            notificationId: Int,
            channelId: String,
            title: String,
            message: String,
            @DrawableRes smallIcon: Int
        ) = NotificationUtil.show(context, notificationId, channelId, title, message, smallIcon)

        @JvmStatic
        fun cancelNotification(context: Context, notificationId: Int) =
            NotificationUtil.cancel(context, notificationId)

        // ── Quick Access: StringUtil ──────────────────────────────────────────

        @JvmStatic
        fun maskEmail(email: String) = StringUtil.run { email.maskEmail() }

        @JvmStatic
        fun maskPhone(phone: String, visibleDigits: Int = 4) =
            StringUtil.run { phone.maskPhone(visibleDigits) }

        @JvmStatic
        fun toInitials(name: String, max: Int = 2) =
            StringUtil.run { name.toInitials(max) }

        @JvmStatic
        fun capitalizeWords(text: String) = StringUtil.run { text.capitalizeWords() }

        @JvmStatic
        fun toSlug(text: String) = StringUtil.run { text.toSlug() }
    }
}
