package com.xihad.androidutils.utils

import java.util.regex.Pattern

object ValidationUtil {

    // ── Email ─────────────────────────────────────────────────────────────────

    fun isValidEmail(email: String): Boolean =
        Pattern.compile("^[A-Z0-9._%+\\-]+@[A-Z0-9.\\-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE)
            .matcher(email.trim()).matches()

    // ── Phone ─────────────────────────────────────────────────────────────────

    /** Validates an international phone number (E.164-ish: +1234567890, 10–15 digits). */
    fun isValidPhone(phone: String): Boolean =
        Pattern.compile("^\\+?[1-9]\\d{9,14}$").matcher(phone.trim()).matches()

    /** Validates a Bangladeshi mobile number (01XXXXXXXXX, 11 digits). */
    fun isValidBDPhone(phone: String): Boolean =
        Pattern.compile("^(?:\\+?88)?01[3-9]\\d{8}$").matcher(phone.trim()).matches()

    // ── URL ───────────────────────────────────────────────────────────────────

    fun isValidUrl(url: String): Boolean =
        Pattern.compile(
            "^(https?|ftp)://[\\w\\-]+(\\.[\\w\\-]+)+([\\w.,@?^=%&:/~+#\\-_]*[\\w@?^=%&/~+#\\-_])?$",
            Pattern.CASE_INSENSITIVE
        ).matcher(url.trim()).matches()

    // ── Password ──────────────────────────────────────────────────────────────

    enum class PasswordStrength { WEAK, FAIR, STRONG, VERY_STRONG }

    /**
     * Evaluates password strength.
     * WEAK      < 6 chars or only one character type
     * FAIR      6+ chars, 2 character types
     * STRONG    8+ chars, 3 character types
     * VERY_STRONG 10+ chars, all 4 character types
     */
    fun getPasswordStrength(password: String): PasswordStrength {
        if (password.length < 6) return PasswordStrength.WEAK
        var score = 0
        if (password.any { it.isLowerCase() }) score++
        if (password.any { it.isUpperCase() }) score++
        if (password.any { it.isDigit() }) score++
        if (password.any { !it.isLetterOrDigit() }) score++
        return when {
            password.length >= 10 && score == 4 -> PasswordStrength.VERY_STRONG
            password.length >= 8 && score >= 3  -> PasswordStrength.STRONG
            score >= 2                           -> PasswordStrength.FAIR
            else                                 -> PasswordStrength.WEAK
        }
    }

    fun isStrongPassword(password: String): Boolean =
        getPasswordStrength(password) >= PasswordStrength.STRONG

    // ── Credit Card ───────────────────────────────────────────────────────────

    /** Validates a credit card number using the Luhn algorithm. */
    fun isValidCreditCard(number: String): Boolean {
        val digits = number.replace(" ", "").replace("-", "")
        if (digits.length < 13 || digits.length > 19) return false
        if (!digits.all { it.isDigit() }) return false
        var sum = 0
        var alternate = false
        for (i in digits.length - 1 downTo 0) {
            var n = digits[i].digitToInt()
            if (alternate) {
                n *= 2
                if (n > 9) n -= 9
            }
            sum += n
            alternate = !alternate
        }
        return sum % 10 == 0
    }

    enum class CardType { VISA, MASTERCARD, AMEX, DISCOVER, UNKNOWN }

    fun getCardType(number: String): CardType {
        val n = number.replace(" ", "")
        return when {
            n.matches(Regex("^4[0-9]{12}(?:[0-9]{3})?\$"))            -> CardType.VISA
            n.matches(Regex("^5[1-5][0-9]{14}\$"))                    -> CardType.MASTERCARD
            n.matches(Regex("^3[47][0-9]{13}\$"))                     -> CardType.AMEX
            n.matches(Regex("^6(?:011|5[0-9]{2})[0-9]{12}\$"))       -> CardType.DISCOVER
            else                                                        -> CardType.UNKNOWN
        }
    }

    // ── IP Address ────────────────────────────────────────────────────────────

    fun isValidIPv4(ip: String): Boolean =
        Pattern.compile(
            "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\$"
        ).matcher(ip.trim()).matches()

    fun isValidIPv6(ip: String): Boolean =
        Pattern.compile(
            "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}\$"
        ).matcher(ip.trim()).matches()

    // ── Misc ──────────────────────────────────────────────────────────────────

    /** Returns true if the string contains only digits. */
    fun isNumeric(value: String): Boolean = value.isNotEmpty() && value.all { it.isDigit() }

    /** Returns true if the string contains only letters. */
    fun isAlpha(value: String): Boolean = value.isNotEmpty() && value.all { it.isLetter() }

    /** Returns true if the string contains only letters and digits. */
    fun isAlphanumeric(value: String): Boolean = value.isNotEmpty() && value.all { it.isLetterOrDigit() }

    /** Validates a PIN (numeric only, default 4–6 digits). */
    fun isValidPin(pin: String, minLength: Int = 4, maxLength: Int = 6): Boolean =
        pin.length in minLength..maxLength && pin.all { it.isDigit() }

    /** Returns true if the string is a valid hex color (#RGB or #RRGGBB). */
    fun isValidHexColor(color: String): Boolean =
        color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})\$"))
}
