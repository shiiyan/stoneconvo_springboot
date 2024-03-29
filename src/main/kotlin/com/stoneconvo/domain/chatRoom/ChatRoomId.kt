package com.stoneconvo.domain.chatRoom

import com.stoneconvo.common.domain.ValueObject
import com.stoneconvo.common.helper.Helper

data class ChatRoomId(val value: String) : ValueObject() {
    companion object {
        fun create() = ChatRoomId(value = Helper.generateRandomId())
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(value))) { "MessageId's pattern is invalid." }
    }
}
