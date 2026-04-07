# AndroidUtils

<img src="https://github.com/xihadulislam/androidUtils/blob/master/ss/android_utils.png" alt="AndroidUtils" width="200">

[![](https://jitpack.io/v/xihadulislam/AndroidUtils.svg)](https://jitpack.io/#xihadulislam/AndroidUtils)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A modern, robust Android utility library — works from both **Kotlin** and **Java**.  
Zero memory leaks. All utils are stateless or use `WeakReference` / `applicationContext` where needed.

---

## Setup

### Step 1 — Add JitPack repository

**Kotlin DSL (`settings.gradle.kts`):**
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Groovy DSL (`settings.gradle`):**
```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2 — Add the dependency

**Kotlin DSL:**
```kotlin
implementation("com.github.xihadulislam:AndroidUtils:v3.1.1")
```

**Groovy DSL:**
```groovy
implementation 'com.github.xihadulislam:AndroidUtils:v3.1.1'
```

---

## Features

| Utility | Description |
|---|---|
| `SharedPreferences` | Thread-safe key-value storage |
| `Encryption` | AES-128, MD5, SHA-1/256/512, Base64 |
| `Validation` | Email, phone, URL, password strength, credit card, IP |
| `Network` | Internet availability checks |
| `Clipboard` | Copy, paste, clear |
| `View / Animations` | Fade, scale, shake, visibility, enable/disable |
| `Bitmap` | Resize, rotate, crop circle, save to gallery |
| `StatusBar` | Edge-to-edge, immersive mode, icon color |
| `Notification` | Channel creation, show/progress/cancel |
| `Storage` | Internal/external space, cache size, clear cache |
| `Number` | Formatting, Bangla digits, number-to-words |
| `Time` | Date formatting, relative time, date helpers |
| `String` | Mask, initials, slug, camelCase, snake_case |
| `Payment` | Tax calculations, cash suggestions |
| `Color` | Lighten, darken, hex conversion |
| `Device` | Device info, emulator detection |
| `AppInfo` | Version, debug check, Play Store, app settings |
| `Intent` | Activity navigation, social sharing |
| `Screenshot` | Capture views, screen protection |
| `Debounce` | Per-instance debounce, no global state |
| `SnackBar` | Styled success/error/warning/info bars |
| `Text` | Spannable helpers, rich text formatting |
| `Log` | Debug, error, info, long-message logging |

---

## Usage

### SharedPreferences

```kotlin
val prefs = AndroidUtils.getSharePrefSetting(context)

prefs.setString("token", "abc123")
val token = prefs.getString("token")

prefs.setBoolean("logged_in", true)
prefs.setInt("count", 5)
prefs.setLong("timestamp", System.currentTimeMillis())
prefs.setDouble("balance", 99.99)

prefs.remove("token")
prefs.clear()
```

```java
// Java
SharePrefSettings prefs = AndroidUtils.getSharePrefSetting(context);
prefs.setString("token", "abc123");
String token = prefs.getString("token", "");
```

---

### Validation

```kotlin
AndroidUtils.isValidEmail("user@example.com")        // true
AndroidUtils.isValidPhone("+8801712345678")           // true
AndroidUtils.isValidUrl("https://example.com")        // true
AndroidUtils.isValidCreditCard("4111111111111111")    // true
AndroidUtils.isStrongPassword("MyPass@123")           // true

val strength = AndroidUtils.getPasswordStrength("abc") // WEAK

with(ValidationUtil) {
    "01712345678".isValidBDPhone()                    // BD number check
    "192.168.1.1".isValidIPv4()
    "#FF5733".isValidHexColor()
    "1234".isValidPin()
    ValidationUtil.getCardType("4111111111111111")    // VISA
}
```

```java
// Java
AndroidUtils.isValidEmail("user@example.com");
AndroidUtils.isStrongPassword("MyPass@123");
```

---

### Clipboard

```kotlin
AndroidUtils.copyToClipboard(context, "Hello!")
val text = AndroidUtils.pasteFromClipboard(context)
AndroidUtils.clearClipboard(context)

// Full API
with(ClipboardUtil) {
    context.copyText("text", label = "my label")
    context.hasText()
}
```

```java
// Java
AndroidUtils.copyToClipboard(context, "Hello!");
String text = AndroidUtils.pasteFromClipboard(context);
```

---

### Encryption

```kotlin
val encrypted = AndroidUtils.encrypt("hello world")
val decrypted = AndroidUtils.decrypt(encrypted)

with(EncryptionUtil) {
    // Custom key & IV (both exactly 16 chars)
    val enc = "secret".encrypt(key = "myCustomKey12345", iv = "myCustomIV123456")
    val dec = enc?.decrypt(key = "myCustomKey12345", iv = "myCustomIV123456")

    val md5    = "password".md5
    val sha256 = "password".sha256
    val sha512 = "password".sha512

    val b64 = "hello".toBase64()
    val raw = b64.fromBase64()

    val key = EncryptionUtil.generateAesKey()   // secure random 16-char key
}
```

---

### Network

```kotlin
AndroidUtils.isInternetAvailable(context)

with(AppUtil) {
    context.isOnline()
    context.isOnline(
        failBlock    = { showNoInternet() },
        successBlock = { fetchData() }
    )
}
```

---

### View Utilities

```kotlin
with(ViewUtil) {
    view.visible();  view.gone();  view.invisible()
    view.toggleVisibility()
    view visibleIf isLoggedIn
    view goneIf isLoading

    button.enable();  button.disable()

    view.resize(200, 100)
    view.setMargins(left = 16, top = 8, right = 16, bottom = 8)

    view.fadeIn()
    view.fadeOut()
    view.scaleIn(duration = 300L)
    view.scaleOut()
    view.shake()
}
```

---

### Bitmap

```kotlin
with(BitmapUtil) {
    bitmap.resize(300, 300)
    bitmap.scaleToFit(512)
    bitmap.rotate(90f)
    bitmap.flip(horizontal = true)
    bitmap.toCircle()
    bitmap.toRoundedCorners(24f)
    bitmap.toJpegByteArray(quality = 80)
    bitmap.saveToGallery(context, "photo_name")   // returns Uri?
    bitmap.saveToCache(context)                   // returns File
    view.toBitmap()
}
```

---

### Status Bar

```kotlin
with(StatusBarUtil) {
    activity.setStatusBarColor(Color.WHITE)
    activity.setStatusBarIconsDark(true)        // dark icons for light bg
    activity.makeStatusBarTransparent()
    activity.enableEdgeToEdge()
    activity.enterImmersiveMode()
    activity.exitImmersiveMode()
    activity.hideStatusBar()
    activity.showStatusBar()
    activity.keepScreenOn()
    activity.setNavigationBarColor(Color.BLACK)
    val height = activity.getStatusBarHeight()
}
```

---

### Notification

```kotlin
// Create channel once (e.g. in Application.onCreate)
AndroidUtils.createNotificationChannel(context, "main_channel", "General")

// Show notification
AndroidUtils.showNotification(context, 1, "main_channel", "Title", "Body", R.drawable.ic_notif)

// Cancel
AndroidUtils.cancelNotification(context, 1)

// Full API
with(NotificationUtil) {
    NotificationUtil.showProgress(context, 2, "main_channel", "Uploading", "45%", R.drawable.ic_upload, max = 100, progress = 45)
    NotificationUtil.cancelAll(context)
    NotificationUtil.areNotificationsEnabled(context)
}
```

---

### Storage

```kotlin
val free  = AndroidUtils.getFreeInternalStorage()
val cache = AndroidUtils.getCacheSize(context)
AndroidUtils.clearCache(context)
AndroidUtils.formatStorageSize(free)         // "1.2 GB"

with(StorageUtil) {
    StorageUtil.getTotalInternalStorage()
    StorageUtil.getUsedInternalStorage()
    StorageUtil.isExternalStorageAvailable()
    StorageUtil.getFreeExternalStorage()
}
```

---

### String Utilities

```kotlin
AndroidUtils.maskEmail("xihad@gmail.com")           // "xi*****@gmail.com"
AndroidUtils.maskPhone("01712345678")                // "*******5678"
AndroidUtils.toInitials("Xihad Islam")              // "XI"
AndroidUtils.capitalizeWords("hello world")          // "Hello World"
AndroidUtils.toSlug("Hello World! 2024")             // "hello-world-2024"

with(StringUtil) {
    "helloWorld".toSnakeCase()                       // "hello_world"
    "hello_world".toCamelCase()                      // "helloWorld"
    "4111111111111111".maskCreditCard()              // "**** **** **** 1111"
    "racecar".isPalindrome()                         // true
    "  Hello   World  ".removeWhitespace()
    "abc123def".digitsOnly()                         // "123"
    "abc123def".lettersOnly()                        // "abcdef"
    "Hello World".wordCount()                        // 2
    "Long text here".truncate(8)                     // "Long tex…"
    null.orDefault("fallback")                       // "fallback"
}
```

---

### Number Utilities

```kotlin
AndroidUtils.numberToWords(1500)                     // "One Thousand Five Hundred"
AndroidUtils.numberInBangla("12-10-2024")            // "১২-১০-২০২৪"
AndroidUtils.getDigitBanglaFromEnglish("1234")       // "১২৩৪"
AndroidUtils.getDigitEnglishFromBangla("১২৩৪")      // "1234"

with(NumberUtils) {
    1.appendOrdinal()                                // "1st"
    11.appendOrdinal()                               // "11th"
    1234567.formatWithCommas()                       // "1,234,567"
    1048576L.formatBytes()                           // "1.0 MB"
    3.14159f.roundOff()                              // "3.14"
    3.14159.roundOff(digits = 4)                     // "3.1416"
}
```

---

### Time Utilities

```kotlin
with(TimeUtils) {
    val now = TimeUtils.getCurrentTime()
    Date().format("dd MMM yyyy")                     // "07 Apr 2026"
    System.currentTimeMillis().toFormattedDate()     // "2026-04-07"
    pastTimestamp.toRelativeTime()                   // "2 hours ago"
    futureTimestamp.toRelativeTime()                 // "in 3 days"
    TimeUtils.daysBetween(fromDate, toDate)
    date.isToday();  date.isPast();  date.isFuture()
    TimeUtils.milliSecondToHMS(90000L)               // "00:01:30"
    TimeUtils.getToday()                             // "2026-04-07"
}
```

---

### Payment Utilities

```kotlin
AndroidUtils.getIncludingTax(100.0, 20.0)           // VAT included in price
AndroidUtils.getExcludingTax(100.0, 20.0)           // VAT on top of price
AndroidUtils.twoDigitDouble(14.4444)                // 14.44
AndroidUtils.twoDigitString(14.364)                 // "14.36"
AndroidUtils.stringToNumber("13.5")                 // 13.5

with(PaymentUtils) {
    PaymentUtils.getCashOption(87.50)               // [87.50, 88.00, 90.00, 100.00, Custom]
    99.99.toPriceAmount()                           // "99.99"
    1234567.89.toPriceAmount()                      // "1,234,567.89"
}
```

---

### Color Utilities

```kotlin
with(ColorUtil) {
    ColorUtil.setStatusBarColor(activity, R.color.primary)
    ColorUtil.getRandomColor()
    "#FF5733".hexToRGB()                            // Triple(255, 87, 51)
    Color.RED.colorToHexString()                    // "#FF0000"
    Color.BLUE.lighten(0.3f)
    Color.RED.darken(0.2f)
    Color.BLACK.isDark()                            // true
    imageView.setTint(Color.GREEN)
    textView.setDrawableColor(R.color.icon_tint)
}
```

---

### Device Info

```kotlin
DeviceInfoUtil.getDeviceName()                      // "Samsung Galaxy S24"
DeviceInfoUtil.getDeviceId(activity)                // Android ID
DeviceInfoUtil.getPrimaryAbi()                      // "arm64-v8a"
DeviceInfoUtil.getSupportedAbis()
DeviceInfoUtil.isEmulator()
DeviceInfoUtil.getDeviceSuperInfo()                 // full debug string
```

---

### App Info

```kotlin
AndroidUtils.getVersionName(context)                // "3.1.1"
AndroidUtils.getVersionCode(context)                // 311
AndroidUtils.isDebugBuild(context)                  // true/false
AndroidUtils.isAppInstalled(context, "com.whatsapp")
AndroidUtils.openPlayStore(context)
AndroidUtils.openAppSettings(context)

with(AppInfoUtil) {
    AppInfoUtil.launchApp(context, "com.instagram.android")
    AppInfoUtil.isInstalledFromPlayStore(context)
    AppInfoUtil.getInstallerPackage(context)
}
```

---

### Debounce

```kotlin
// Each screen/component holds its own instance
private val debounce = DebounceUtils()

searchView.addTextChangedListener {
    debounce.run(400) { doSearch(it) }
}

// Fixed delays
debounce.run300 { doSomething() }
debounce.run500 { doSomethingElse() }

// Cancel
debounce.cancel()
```

```java
// Java
private final DebounceUtils debounce = AndroidUtils.newDebounce();

searchView.addTextChangedListener(text -> {
    debounce.run(400, () -> doSearch(text));
});
```

---

### Screenshot

```kotlin
val bitmap = AndroidUtils.takeScreenshotOfView(rootView)
AndroidUtils.protectToScreenshot(activity)
```

---

### Click Effect

```kotlin
AndroidUtils.applyClickEffect(view)

with(AppUtil) {
    button.onClick(debounceDuration = 500L) {
        // fires once per 500ms
    }
}
```

---

### SnackBar

```kotlin
val snack = AndroidUtils.getSnackBar(activity)

snack.snackBar("Default message")
snack.successSnack(rootView, "Saved!")
snack.errorSnack(rootView, "Something went wrong")
snack.warningSnack(rootView, "Check your connection")
snack.infoSnack(rootView, "Tap to learn more") { /* on click */ }
```

---

### Intent & Navigation

```kotlin
val intent = AndroidUtils.getIntent()

intent.startNextActivity(activity, SecondActivity::class.java)
intent.startNextActivity(activity, SecondActivity::class.java, isFinish = true)
intent.afterNextActivity(activity, milliSecond = 2000, SecondActivity::class.java)
intent.startShareIntent(activity, "Check this out!")
intent.startFeedbackActivity(activity, "support@example.com")
intent.startRateAppActivity(activity)
intent.startWhatsAppIntent(activity, "Hello!")
intent.startFacebookIntent(activity, "https://...")
intent.startTwitterIntent(activity, "https://...")
```

---

### Unique ID

```kotlin
val uid = AndroidUtils.uId()    // e.g. "42N35260Y390345AM"
```

---

## Java Usage

All methods are annotated with `@JvmStatic` and use `Runnable` instead of lambdas where needed:

```java
// Init
AndroidUtils.init();

// Basic usage
AndroidUtils.toast(context, "Hello");
AndroidUtils.isInternetAvailable(context);
AndroidUtils.copyToClipboard(context, "text");
AndroidUtils.isValidEmail("user@example.com");
AndroidUtils.getVersionName(context);

// SharedPreferences
SharePrefSettings prefs = AndroidUtils.getSharePrefSetting(context);
prefs.setString("key", "value");

// Debounce — each caller holds its own instance
private final DebounceUtils debounce = AndroidUtils.newDebounce();
debounce.run(300, () -> doSearch());

// Post delayed
AndroidUtils.postDelayed(2000, () -> doSomething());
```

---

## Memory Safety

| Pattern | How handled |
|---|---|
| Activity in SnackBar | `WeakReference<Activity>` + `isFinishing`/`isDestroyed` guard |
| Activity in delayed navigation | `WeakReference<Activity>` in `afterNextActivity` |
| MediaPlayer Context | Always uses `context.applicationContext` |
| Debounce callbacks | Per-instance `DebounceUtils` — no global state |
| Click effects | `WeakReference<View>` in `ClickEffect` |
| SharedPreferences singleton | Uses `applicationContext` only |
| All other utils | Stateless `object` — no stored references |

---

## Sample Project

Clone this repo and check out the [app](https://github.com/xihadulislam/AndroidUtils/blob/master/app) module.

---

## Author

**xihad islam**
- [LinkedIn](https://www.linkedin.com/in/xihad-islam-315417185/)
- [GitHub](https://github.com/xihadulislam)
- [Twitter](https://twitter.com/islamxihad)

---

## License

```
Copyright 2024 xihad islam

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
