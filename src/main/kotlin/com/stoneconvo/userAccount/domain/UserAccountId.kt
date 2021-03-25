package com.stoneconvo.userAccount.domain

import com.stoneconvo.common.helper.Helper

data class UserAccountId(val value: String) {
    companion object {
        fun create() = UserAccountId(value = Helper.generateRandomId())
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(value))) { "UserAccountId's pattern is invalid." }
    }
}
