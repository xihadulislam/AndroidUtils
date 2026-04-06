package com.xihad.androidutils.utils

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val DEFAULT_KEY = "aesEncryptionKey"   // 16-byte AES-128 key
    private const val DEFAULT_IV  = "encryptionIntVec"   // 16-byte IV

    // ── AES-128 CBC encrypt / decrypt ─────────────────────────────────────────

    /** Encrypts [this] string with the given [key] and [iv] (both must be exactly 16 chars). */
    fun String.encrypt(key: String = DEFAULT_KEY, iv: String = DEFAULT_IV): String? = runCatching {
        val cipher = buildCipher(Cipher.ENCRYPT_MODE, key, iv)
        Base64.encodeToString(cipher.doFinal(toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
    }.getOrNull()

    /** Decrypts a Base64-encoded [this] string with the given [key] and [iv]. */
    fun String.decrypt(key: String = DEFAULT_KEY, iv: String = DEFAULT_IV): String? = runCatching {
        val cipher = buildCipher(Cipher.DECRYPT_MODE, key, iv)
        String(cipher.doFinal(Base64.decode(this, Base64.DEFAULT)), Charsets.UTF_8)
    }.getOrNull()

    private fun buildCipher(mode: Int, key: String, iv: String): Cipher {
        require(key.length == 16) { "AES key must be exactly 16 characters" }
        require(iv.length == 16)  { "IV must be exactly 16 characters" }
        return Cipher.getInstance("AES/CBC/PKCS5PADDING").also {
            it.init(
                mode,
                SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES"),
                IvParameterSpec(iv.toByteArray(Charsets.UTF_8))
            )
        }
    }

    /** Generates a cryptographically random 16-character key suitable for AES-128. */
    fun generateAesKey(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*"
        val rng = SecureRandom()
        return (1..16).map { chars[rng.nextInt(chars.length)] }.joinToString("")
    }

    // ── Hashing ───────────────────────────────────────────────────────────────

    val String.md5: String
        get() = hash("MD5")

    val String.sha1: String
        get() = hash("SHA-1")

    val String.sha256: String
        get() = hash("SHA-256")

    val String.sha512: String
        get() = hash("SHA-512")

    private fun String.hash(algorithm: String): String =
        MessageDigest.getInstance(algorithm)
            .digest(toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }

    // ── Base64 convenience ────────────────────────────────────────────────────

    fun String.toBase64(): String =
        Base64.encodeToString(toByteArray(Charsets.UTF_8), Base64.NO_WRAP)

    fun String.fromBase64(): String =
        String(Base64.decode(this, Base64.NO_WRAP), Charsets.UTF_8)
}
