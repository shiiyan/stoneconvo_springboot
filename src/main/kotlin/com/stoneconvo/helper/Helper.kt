package com.stoneconvo.helper

import java.util.UUID

object Helper {
    fun generateRandomId() =
        UUID.randomUUID().toString().replace("-", "").substring(12)
}
