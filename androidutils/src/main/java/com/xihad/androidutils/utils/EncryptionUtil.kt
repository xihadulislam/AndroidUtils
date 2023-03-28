package com.xihad.androidutils.utils

import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {


    fun String.encrypt(): String? {
        val key = "aesEncryptionKey"
        val initVector = "encryptionIntVec"
        try {
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(this.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }

    fun String.decrypt(): String? {
        val key = "aesEncryptionKey"
        val initVector = "encryptionIntVec"
        try {
            val iv = IvParameterSpec(initVector.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))
            return String(original)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }
        return null
    }


    val String.md5: String
        get() {
            val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }


    val String.sha1: String
        get() {
            val bytes = MessageDigest.getInstance("SHA-1").digest(this.toByteArray())
            return bytes.joinToString("") {
                "%02x".format(it)
            }
        }


}