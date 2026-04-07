package com.xihad.androidutils.utils

import java.util.Locale

object StringUtil {

    // ── Masking ───────────────────────────────────────────────────────────────

    /**
     * Masks an email address. "example@mail.com" → "ex*****@mail.com"
     */
    fun String.maskEmail(): String {
        val atIndex = indexOf('@')
        if (atIndex < 2) return this
        val visible = 2
        val local = substring(0, atIndex)
        val domain = substring(atIndex)
        return local.take(visible) + "*".repeat(local.length - visible) + domain
    }

    /**
     * Masks a phone number, showing only the last [visibleDigits] digits.
     * "01712345678" → "*******5678"
     */
    fun String.maskPhone(visibleDigits: Int = 4): String {
        if (length <= visibleDigits) return this
        return "*".repeat(length - visibleDigits) + takeLast(visibleDigits)
    }

    /**
     * Masks a credit card number, showing only the last 4 digits.
     * "4111111111111111" → "**** **** **** 1111"
     */
    fun String.maskCreditCard(): String {
        val digits = replace(" ", "")
        if (digits.length < 4) return this
        return "**** **** **** ${digits.takeLast(4)}"
    }

    // ── Name / Text formatting ────────────────────────────────────────────────

    /**
     * Returns initials from a full name (up to [maxInitials] letters).
     * "Xihad Islam" → "XI"
     */
    fun String.toInitials(maxInitials: Int = 2): String =
        trim().split(Regex("\\s+"))
            .filter { it.isNotEmpty() }
            .take(maxInitials)
            .joinToString("") { it.first().uppercaseChar().toString() }

    /**
     * Capitalizes the first letter of every word.
     * "hello world" → "Hello World"
     */
    fun String.capitalizeWords(): String =
        trim().split(Regex("\\s+")).joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercaseChar() }
        }

    /**
     * Converts a string to a URL-friendly slug.
     * "Hello World! 2024" → "hello-world-2024"
     */
    fun String.toSlug(): String =
        trim()
            .lowercase(Locale.ROOT)
            .replace(Regex("[^a-z0-9\\s-]"), "")
            .replace(Regex("\\s+"), "-")
            .replace(Regex("-+"), "-")
            .trim('-')

    /**
     * Converts snake_case or kebab-case to camelCase.
     * "hello_world" → "helloWorld"
     */
    fun String.toCamelCase(): String =
        split(Regex("[_\\-\\s]+")).mapIndexed { i, word ->
            if (i == 0) word.lowercase(Locale.ROOT)
            else word.replaceFirstChar { it.uppercaseChar() }
        }.joinToString("")

    /**
     * Converts camelCase or PascalCase to snake_case.
     * "helloWorld" → "hello_world"
     */
    fun String.toSnakeCase(): String =
        replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase(Locale.ROOT)

    // ── Counting ──────────────────────────────────────────────────────────────

    /** Counts the number of words in a string. */
    fun String.wordCount(): Int = trim().split(Regex("\\s+")).count { it.isNotEmpty() }

    /** Counts occurrences of [sub] in this string. */
    fun String.countOccurrences(sub: String): Int {
        if (sub.isEmpty()) return 0
        var count = 0
        var index = 0
        while (true) {
            index = indexOf(sub, index)
            if (index == -1) break
            count++
            index += sub.length
        }
        return count
    }

    // ── Misc ──────────────────────────────────────────────────────────────────

    /** Returns true if the string is a palindrome (ignores case and spaces). */
    fun String.isPalindrome(): Boolean {
        val clean = lowercase(Locale.ROOT).filter { it.isLetterOrDigit() }
        return clean == clean.reversed()
    }

    /** Removes all whitespace from the string. */
    fun String.removeWhitespace(): String = replace(Regex("\\s"), "")

    /** Returns the string or a [default] value if blank. */
    fun String?.orDefault(default: String): String = if (isNullOrBlank()) default else this

    /** Wraps the string with [prefix] and [suffix]. */
    fun String.wrap(prefix: String, suffix: String = prefix): String = "$prefix$this$suffix"

    /** Pads the string to [length] characters on both sides, centered. */
    fun String.center(length: Int, padChar: Char = ' '): String {
        if (this.length >= length) return this
        val totalPad = length - this.length
        val leftPad = totalPad / 2
        val rightPad = totalPad - leftPad
        return padChar.toString().repeat(leftPad) + this + padChar.toString().repeat(rightPad)
    }

    /** Extracts only digits from a string. "abc123def456" → "123456" */
    fun String.digitsOnly(): String = filter { it.isDigit() }

    /** Extracts only letters from a string. "abc123def" → "abcdef" */
    fun String.lettersOnly(): String = filter { it.isLetter() }
}
