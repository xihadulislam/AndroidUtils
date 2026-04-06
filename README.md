# AndroidUtils

<img src="https://github.com/xihadulislam/androidUtils/blob/master/ss/android_utils.png" alt="AndroidUtils" width="200">

[![](https://jitpack.io/v/xihadulislam/AndroidUtils.svg)](https://jitpack.io/#xihadulislam/AndroidUtils)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A modern, robust Android utility library that eliminates boilerplate code for common Android tasks.

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
implementation("com.github.xihadulislam:AndroidUtils:v3.1.0")
```

**Groovy DSL:**
```groovy
implementation 'com.github.xihadulislam:AndroidUtils:v3.1.0'
```

---

## Features

| Utility | Description |
|---|---|
| `SharedPreferences` | Thread-safe encrypted key-value storage |
| `Encryption` | AES-128, MD5, SHA-1/256/512, Base64 |
| `Network` | Internet availability checks |
| `View` | Animations, visibility, enable/disable |
| `Number` | Formatting, Bangla digits, number-to-words |
| `Time` | Date formatting, relative time, date helpers |
| `Payment` | Tax calculations, cash suggestions |
| `Color` | Lighten, darken, hex conversion |
| `Device` | Device info, emulator detection |
| `Intent` | Activity navigation, social sharing |
| `Screenshot` | Capture views, screen protection |
| `SnackBar` | Styled success/error/warning/info bars |
| `Text` | Spannable helpers, rich text formatting |

---

## Usage

### SharedPreferences

```kotlin
val prefs = AndroidUtils.getSharePrefSetting(context)

prefs.setString("token", "abc123")
val token = prefs.getString("token")

prefs.setBoolean("logged_in", true)
val isLoggedIn = prefs.getBoolean("logged_in")

prefs.setInt("count", 5)
prefs.setLong("timestamp", System.currentTimeMillis())
prefs.setDouble("balance", 99.99)

prefs.remove("token")
prefs.clear()
```

---

### Encryption

```kotlin
// AES-128 encrypt / decrypt
val encrypted = AndroidUtils.encrypt("hello world")
val decrypted = AndroidUtils.decrypt(encrypted)

// With custom key & IV (both must be exactly 16 chars)
with(EncryptionUtil) {
    val enc = "secret".encrypt(key = "myCustomKey12345", iv = "myCustomIV123456")
    val dec = enc?.decrypt(key = "myCustomKey12345", iv = "myCustomIV123456")

    // Hashing
    val md5    = "password".md5
    val sha1   = "password".sha1
    val sha256 = "password".sha256
    val sha512 = "password".sha512

    // Base64
    val encoded = "hello".toBase64()
    val decoded = encoded.fromBase64()

    // Generate a secure random AES key
    val key = EncryptionUtil.generateAesKey()
}
```

---

### Network

```kotlin
AndroidUtils.isInternetAvailable(context)   // true / false

context.isOnline()                          // extension, true / false

context.isOnline(
    failBlock    = { showNoInternetDialog() },
    successBlock = { fetchData() }
)
```

---

### View Utilities

```kotlin
with(ViewUtil) {
    // Visibility
    view.visible()
    view.gone()
    view.invisible()
    view.toggleVisibility()
    view visibleIf isLoggedIn
    view goneIf isLoading

    // Enable / disable (with alpha feedback)
    button.enable()
    button.disable()

    // Size & margin
    view.setWidth(200)
    view.setHeight(100)
    view.resize(200, 100)
    view.setMargins(left = 16, top = 8, right = 16, bottom = 8)

    // Animations
    view.fadeIn()
    view.fadeOut()
    view.scaleIn(duration = 300L)
    view.scaleOut()
    view.shake()                        // error shake animation

    // Callbacks
    view.fadeIn(duration = 250L) { doAfterFade() }
}
```

---

### Click Effect

```kotlin
AndroidUtils.applyClickEffect(view)

// Debounced click (prevents double-tap)
with(AppUtil) {
    button.onClick(debounceDuration = 500L) {
        // only fires once per 500ms
    }
}
```

---

### Number Utilities

```kotlin
with(NumberUtils) {
    // Number to words
    NumberUtils.numberToWords(1500)         // "One Thousand Five Hundred"

    // Ordinals
    1.appendOrdinal()                        // "1st"
    11.appendOrdinal()                       // "11th"
    22.appendOrdinal()                       // "22nd"

    // Thousand-separator formatting
    1234567.formatWithCommas()               // "1,234,567"
    9999999.99.formatWithCommas()            // "9,999,999.99"

    // File size formatting
    1048576L.formatBytes()                   // "1.0 MB"
    2500000000L.formatBytes()               // "2.3 GB"

    // Rounding
    3.14159f.roundOff()                      // "3.14"
    3.14159.roundOff(digits = 4)             // "3.1416"

    // Bangla digits
    NumberUtils.numberInBangla("12-10-2024")           // "১২-১০-২০২৪"
    NumberUtils.getDigitBanglaFromEnglish("1234")      // "১২৩৪"
    NumberUtils.getDigitEnglishFromBangla("১২৩৪")     // "1234"
}
```

---

### Time Utilities

```kotlin
with(TimeUtils) {
    // Current time
    val now: Long = TimeUtils.getCurrentTime()
    val date: Date = TimeUtils.getCurrentTimeAndDate()

    // Format a date
    date.format("dd MMM yyyy")              // "06 Apr 2026"
    date.format("hh:mm a")                 // "09:30 AM"

    // Timestamp to formatted string
    System.currentTimeMillis().toFormattedDate("yyyy-MM-dd")

    // Relative time
    pastTimestamp.toRelativeTime()          // "2 hours ago"
    futureTimestamp.toRelativeTime()        // "3 days from now"

    // Days between two dates
    TimeUtils.daysBetween(fromDate, toDate)

    // Checks
    date.isToday()
    date.isPast()
    date.isFuture()

    // Parse a date string
    TimeUtils.parseDate("2026-04-06", pattern = "yyyy-MM-dd")

    // Milliseconds to HH:MM:SS
    TimeUtils.milliSecondToHMS(90000L)      // "00:01:30"

    // Today's date string
    TimeUtils.getToday()                    // "2026-04-06"
}
```

---

### Payment Utilities

```kotlin
val includingTax = AndroidUtils.getIncludingTax(100.0, 20.0)   // VAT included
val excludingTax = AndroidUtils.getExcludingTax(100.0, 20.0)   // VAT on top

val formatted = AndroidUtils.twoDigitDouble(14.4444)            // 14.44
val str       = AndroidUtils.twoDigitString(14.364)             // "14.36"
val num       = AndroidUtils.stringToNumber("13.5")             // 13.5

// Cash suggestions for a given total
with(PaymentUtils) {
    val options = PaymentUtils.getCashOption(87.50)
    // → [87.50, 88.00, 90.00, 100.00, "Custom"]
    
    99.99.toPriceAmount()                   // "99.99"
    1234567.89.toPriceAmount()              // "1,234,567.89"
}
```

---

### Color Utilities

```kotlin
with(ColorUtil) {
    // Colored drawable
    val drawable = ColorUtil.getColoredDrawable(context, R.drawable.ic_icon, Color.RED)

    // Status bar
    ColorUtil.setStatusBarColor(activity, R.color.primary)

    // Random pastel color
    val color = ColorUtil.getRandomColor()

    // Hex ↔ RGB
    val (r, g, b) = "#FF5733".hexToRGB()
    val hex = Color.RED.colorToHexString()   // "#FF0000"

    // Lighten / darken
    Color.BLUE.lighten(0.3f)
    Color.RED.darken(0.2f)

    // Check if dark (useful for choosing text color)
    if (backgroundColor.isDark()) textView.setTextColor(Color.WHITE)

    // Tint image
    imageView.setTint(Color.GREEN)

    // Tint TextView compound drawables
    textView.setDrawableColor(R.color.icon_tint)
}
```

---

### Text / Spannable Utilities

```kotlin
with(TextUtils) {
    // Colorize part of a TextView
    textView.setColorOfSubstring("click here", Color.BLUE)
    textView.setColorOfSubstringRes("click here", R.color.link)

    // Rich text spans
    "Hello".bold()
    "World".italic()
    "Link".underline()
    "Price".foregroundColor(Color.RED)
    "Note".backgroundColor(Color.YELLOW)
    "small".relativeSize(0.8f)
    "TM".superscript()
    "H2O".subscript()
    "old price".strike()

    // String helpers
    "  Hello   World  ".collapseSpaces()        // "Hello World"
    "Long title text here".truncate(10)          // "Long title…"
    "Long title text here".truncate(10, "...")   // "Long title..."
    listOf("Hello", "World").concatenateLowercase() // "helloworld"
}
```

---

### Device Info

```kotlin
DeviceInfoUtil.getDeviceName()          // "Samsung Galaxy S24"
DeviceInfoUtil.getDeviceId(activity)    // Android ID
DeviceInfoUtil.getPrimaryAbi()          // "arm64-v8a"
DeviceInfoUtil.getSupportedAbis()       // ["arm64-v8a", "armeabi-v7a"]
DeviceInfoUtil.isEmulator()             // true / false
DeviceInfoUtil.getDeviceSuperInfo()     // full debug info string
```

---

### Screenshot

```kotlin
// Capture a view as Bitmap
val bitmap = AndroidUtils.takeScreenshotOfView(rootView)
val bitmap2 = AndroidUtils.takeScreenshotOfView(rootView, width = 500, height = 300)

// Prevent screenshots on this screen
AndroidUtils.protectToScreenshot(activity)
```

---

### SnackBar

```kotlin
val snack = AndroidUtils.getSnackBar(activity)

snack.snackBar("Default message")
snack.successSnack(rootView, "Saved successfully!")
snack.errorSnack(rootView, "Something went wrong")
snack.warningSnack(rootView, "Check your connection")
snack.infoSnack(rootView, "Tap to learn more") { /* on click */ }
```

---

### Intent & Navigation

```kotlin
val intent = AndroidUtils.getIntent()

// Navigate immediately
intent.startNextActivity(activity, SecondActivity::class.java)
intent.startNextActivity(activity, SecondActivity::class.java, isFinish = true)

// Navigate after a delay
intent.afterNextActivity(activity, milliSecond = 2000, SecondActivity::class.java)

// Share / social
intent.startShareIntent(activity, "Check out this app!")
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
