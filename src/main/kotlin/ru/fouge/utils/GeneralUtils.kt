package ru.fouge.utils

import java.security.MessageDigest

fun String.toSha256(): String {
    val md = MessageDigest.getInstance("SHA-256")

    return md.digest(toByteArray()).fold("") {str, it ->
        str + "%02x".format(it)
    }
}
