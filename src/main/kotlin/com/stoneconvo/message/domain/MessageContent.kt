package com.stoneconvo.message.domain

data class MessageContent(val value: String) {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 100
    }

    init {
        validate()
    }

    private fun validate() {
        require(value.length in MIN_LENGTH..MAX_LENGTH) { "MessageContent's length is invalid." }
    }
}
