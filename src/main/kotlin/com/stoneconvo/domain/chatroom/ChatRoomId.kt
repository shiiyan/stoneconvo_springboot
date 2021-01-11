package com.stoneconvo.domain.chatroom

import com.stoneconvo.helpers.Helper

data class ChatRoomId(val id: String) {
    companion object {
        fun create() = ChatRoomId(id = Helper.generateRandomId())
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(id))) { "MessageId's pattern is invalid." }
    }
}
