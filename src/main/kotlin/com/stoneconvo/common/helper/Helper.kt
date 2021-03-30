package com.stoneconvo.common.helper

import java.security.MessageDigest
import java.util.Base64
import java.util.UUID

object Helper {
    fun generateRandomId() =
        UUID.randomUUID().toString().replace("-", "").substring(12)

    fun generateHash(plainText: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hash = md.digest(plainText.toByteArray(Charsets.UTF_8))

        return Base64.getEncoder().encodeToString(hash)
    }
}
