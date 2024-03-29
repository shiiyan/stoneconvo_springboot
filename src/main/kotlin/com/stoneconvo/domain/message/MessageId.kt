package com.stoneconvo.domain.message

import com.stoneconvo.common.domain.ValueObject
import com.stoneconvo.common.helper.Helper

data class MessageId(val value: String) : ValueObject() {
    companion object {
        fun create() = MessageId(value = Helper.generateRandomId())
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(value))) { "MessageId's pattern is invalid." }
    }
}
