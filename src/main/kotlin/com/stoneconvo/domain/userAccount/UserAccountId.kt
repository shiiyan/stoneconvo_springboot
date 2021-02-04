package com.stoneconvo.domain.userAccount

data class UserAccountId(val value: String) {
    companion object {
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(value))) { "UserAccountId's pattern is invalid." }
    }
}
