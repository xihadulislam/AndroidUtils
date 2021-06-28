package com.xihad.myapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils

class SharePrefSettings/*
     * A private Constructor prevents any other class from instantiating.
     */
private constructor(private val mContext: Context) {

    val preference: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null
    private var mEditorOpened = false

    val preferenceEditor: SharedPreferences.Editor?
        get() {
            if (mEditorOpened) {
                return mEditor
            }
            mEditorOpened = true
            mEditor = preference.edit()
            return mEditor
        }

    init {
        preference = getSharedPreferenceFile(mContext, "new_mypref")
    }

    /**
     * if a prefFilename is not defined the getDefaultSharedPreferences is used.
     *
     * @param context should be ApplicationContext not Activity
     * @return
     */
    private fun getSharedPreferenceFile(context: Context, prefFilename: String): SharedPreferences {
        return if (TextUtils.isEmpty(prefFilename)) {
            PreferenceManager
                .getDefaultSharedPreferences(context)
        } else {
            context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)
        }
    }

    fun commitPreferenceEditor() {
        try {
            mEditor!!.commit()
        } catch (e: Exception) {
            // TODO: handle exception
        }

        mEditorOpened = false
    }

    // string
    fun setStringValue(key: String, `val`: String) {

        try {
            mEditor!!.putString(getSecureText(key), getSecureText(`val`))
        } catch (e: Exception) {
            val editor = preference.edit()
            editor.putString(getSecureText(key), getSecureText(`val`))
            editor.apply()
        }

    }

    fun getStringValue(key: String): String {
        val defff = preference.getString(getSecureText(key), "")
        return if (defff!!.equals("", ignoreCase = true)) {
            defff
        } else {
            getPlainText(defff)
        }
    }

    fun getStringValue(key: String, defaultstring: String): String {
        val defff = preference.getString(getSecureText(key), defaultstring)
        return if (defff!!.equals(defaultstring, ignoreCase = true)) {
            defff
        } else {
            getPlainText(defff)
        }
    }

    // int
    fun getIntValue(key: String): Int {
        return preference.getInt(getSecureText(key), 0)
    }
    // int
    fun getIntValue(key: String, defaultvalue: Int): Int {
        return preference.getInt(getSecureText(key), defaultvalue)
    }

    fun setIntValue(key: String, `val`: Int) {

        val editor = preference.edit()
        editor.putInt(getSecureText(key), `val`)
        editor.apply()
    }

    // long
    fun getLongValue(key: String): Long {

        return preference.getLong(getSecureText(key), 0)
    }

    // long
    fun getLongValue(key: String, defaultvalue: Long): Long {

        return preference.getLong(getSecureText(key), defaultvalue)
    }

    fun setLongValue(key: String, `val`: Long) {

        val editor = preference.edit()
        editor.putLong(getSecureText(key), `val`)
        editor.apply()
    }


    // float
    fun getFloatValue(key: String): Float {
        return preference.getFloat(getSecureText(key), -1.0f)
    }

    fun getFloatValue(key: String, `val`: Float): Float {
        return preference.getFloat(getSecureText(key), `val`)
    }

    fun setFloatValue(key: String, `val`: Float) {
        val editor = preference.edit()
        editor.putFloat(getSecureText(key), `val`)
        editor.apply()
    }

    // double
    fun getDoubleValue(key: String): Float {

        return preference.getFloat(getSecureText(key), -1.0f)
    }

    fun setDoubleValue(key: String, `val`: Double) {

        val editor = preference.edit()
        editor.putFloat(getSecureText(key), `val`.toFloat())
        editor.apply()
    }

    // boolean
    fun setBoolValue(key: String, b: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(getSecureText(key), b)
        editor.apply()
    }

    fun removeKeyValue(key: String) {
        try {
            mEditor!!.remove(getSecureText(key))
        } catch (e: Exception) {
            // TODO: handle exception
            val editor = preference.edit()
            editor.remove(getSecureText(key))
            editor.apply()
        }

    }


    fun onResetExcept(data: MutableMap<String, Any>) {
        val editor = preference.edit()
        editor.clear()

        val it = data.entries.iterator()
        while (it.hasNext()) {
            val pair = it.next()

            when (val itemObject = pair.value) {
                is Int -> {
                    this.setIntValue(getSecureText(pair.key), itemObject )
                }
                is String -> {
                    this.setStringValue(getSecureText(pair.key), getSecureText(itemObject ))
                }
                is Boolean -> {
                    this.setBoolValue(getSecureText(pair.key), itemObject )
                }
                is Long -> {
                    this.setLongValue(getSecureText(pair.key), itemObject )
                }
                is Double -> {
                    this.setDoubleValue(getSecureText(pair.key), itemObject )
                }
            }

            it.remove()
        }
        editor.apply()
    }

    fun getBoolValue(key: String): Boolean {
        return preference.getBoolean(getSecureText(key), false)
    }

    fun getBoolValue(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(getSecureText(key), defaultValue)
    }

    private fun getSecureText(plain_data: String): String {
        return plain_data
    }

    private fun getPlainText(encrypted_data: String): String {
        return encrypted_data
    }

    fun clearData(){
        val editor = preference.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var appSettingSingleton: SharePrefSettings? = null
        /* Static 'instance' method */
        fun getInstance(_mContext: Context): SharePrefSettings? {
            if (appSettingSingleton == null)
                appSettingSingleton =
                    SharePrefSettings(
                        _mContext
                    )
                return appSettingSingleton
        }
    }
}