package com.xihad.androidutils.utils

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {


    fun encrypt(value: String): String? {
        val key = "aesEncryptionKey"
        val initVector = "encryptionIntVec"
        try {
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun decrypt(value: String): String? {
        val key = "aesEncryptionKey"
        val initVector = "encryptionIntVec"
        try {
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT))
            return String(original)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }


}