package com.stoneconvo.domain.message

data class MessageId(val id: String) {
    companion object {
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(id))) { "MessageId's pattern is invalid." }
    }
}
