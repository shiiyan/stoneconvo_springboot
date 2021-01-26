package com.stoneconvo.domain.roomMember

import com.stoneconvo.helpers.Helper

data class RoomMemberId(val id: String) {
    companion object {
        fun create() = RoomMemberId(id = Helper.generateRandomId())
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(id))) { "MessageId's pattern is invalid." }
    }
}
