package ru.mirea.seven.practice.utils

import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    return if (email.isEmpty()) {
        false
    } else {
        EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }
}

private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
)
