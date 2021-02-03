package com.stoneconvo.domain.message

import com.stoneconvo.helpers.Helper

data class MessageId(val id: String) {
    companion object {
        fun create() = MessageId(id = Helper.generateRandomId())
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(id))) { "MessageId's pattern is invalid." }
    }
}
