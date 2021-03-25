package com.stoneconvo.chatRoom.domain

data class ChatRoomName(val value: String) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 30
    }

    init {
        validate()
    }

    private fun validate() {
        require(value.length in MIN_LENGTH..MAX_LENGTH) { "ChatRoomName's length is invalid." }
    }
}
