package com.stoneconvo.domain.chatroom.roomMember

data class RoomMemberName(val value: String) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 30
    }

    init {
        validate()
    }

    private fun validate() {
        require(value.length in MIN_LENGTH..MAX_LENGTH) { "RoomMemberName's length is invalid." }
    }
}
