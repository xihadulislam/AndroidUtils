package com.xihad.androidutils.ext

import android.app.Activity
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

object KotlinExtensionFunctions {


    inline fun <T> T.applyIf(condition: Boolean, block: T.() -> Unit): T {
        return if (condition) {
            this.apply(block)
        } else {
            this
        }
    }


    /**
     *
     * uses
     *  val text = "This is text"
    text.printToLog()

    val user = User(
    name = "John",
    id = 1
    )
    user.printToLog() // With default com.xihad.androidutils.ext.number.log tag
    user.printToLog(tag = "USER_INFO") // With custom com.xihad.androidutils.ext.number.log tag

     */

    fun Any?.printToLog(tag: String = "DEBUG_LOG") {
        Log.d(tag, toString())
    }


    /**
     *
     * view.gone()
    view.visible()
    view.invisible()

    // dataFound, loading, and condition should be a valid boolean expression
    view goneIf dataFound
    view visibleIf loading
    view invisibleIf condition
     *
     */

    fun View.gone() = run { visibility = View.GONE }

    fun View.visible() = run { visibility = View.VISIBLE }

    fun View.invisible() = run { visibility = View.INVISIBLE }

    infix fun View.visibleIf(condition: Boolean) =
        run { visibility = if (condition) View.VISIBLE else View.GONE }

    infix fun View.goneIf(condition: Boolean) =
        run { visibility = if (condition) View.GONE else View.VISIBLE }

    infix fun View.invisibleIf(condition: Boolean) =
        run { visibility = if (condition) View.INVISIBLE else View.VISIBLE }


    /**
     * toast("This is toast message")
    toast(R.string.toast_message)
     */

    fun Fragment.toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun Fragment.toast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Activity.toast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    /**

    rootView.snackbar("This is snackbar message")
    rootView.snackbar(R.string.snackbar_message)

    // Custom Duration Length
    rootView.snackbar("This is snackbar message", duration = Snackbar.LENGTH_SHORT)

     */

    fun View.snackBar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }

    fun View.snackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }


    fun Activity.hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun Fragment.hideKeyboard() {
        activity?.apply {
            val imm: InputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = currentFocus ?: View(this)
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    /**
     * params.setMargins(16.px, 16.px, 16.px, 16.px)
     */

    // Convert px to dp
    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    //Convert dp to px
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()


    /**

    val isValidNumber = "1234".isDigitOnly // Return true
    val isValid = "1234abc".isDigitOnly // Return false
    val isOnlyAlphabetic = "abcABC".isAlphabeticOnly // Return true
    val isOnlyAlphabetic2 = "abcABC123".isAlphabeticOnly // Return false
    val isOnlyAlphanumeric = "abcABC123".isAlphanumericOnly // Return true
    val isOnlyAlphanumeric2 = "abcABC@123.".isAlphanumericOnly // Return false

     */

    val String.isDigitOnly: Boolean
        get() = matches(Regex("^\\d*\$"))

    val String.isAlphabeticOnly: Boolean
        get() = matches(Regex("^[a-zA-Z]*\$"))

    val String.isAlphanumericOnly: Boolean
        get() = matches(Regex("^[a-zA-Z\\d]*\$"))


    /**
     *
    if (obj.com.xihad.androidutils.ext.standard.isNull) {
    // Run if object is null
    } else {
    // Run if object is not null
    }

    obj.ifNull {
    // Write code
    }

     */

    val Any?.isNull get() = this == null


    fun Any?.ifNull(block: () -> Unit) = run {
        if (this == null) {
            block()
        }
    }


    /**

    val currentDate = Date().toStringFormat()
    val currentDate2 = Date().toStringFormat(format = "dd-MM-yyyy")
    val date = "2023-01-01".toDate(format = "yyyy-MM-dd")

     */

    fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.parse(this)
    }

    fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        return dateFormatter.format(this)
    }


}