package com.stoneconvo.domain.userAccount

data class UserAccountId(val id: String) {
    companion object {
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(id))) { "UserAccountId's pattern is invalid." }
    }
}
