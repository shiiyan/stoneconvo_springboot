package com.stoneconvo.domain.message

import com.stoneconvo.common.domain.ValueObject

data class MessageContent(val value: String) : ValueObject() {
    companion object {
        private const val MIN_LENGTH = 1
        private const val MAX_LENGTH = 1000
    }

    init {
        validate()
    }

    private fun validate() {
        require(value.length in MIN_LENGTH..MAX_LENGTH) { "MessageContent's length is invalid." }
    }
}
