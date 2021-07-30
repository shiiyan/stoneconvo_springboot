package com.stoneconvo.domain.userAccount

import com.stoneconvo.common.domain.ValueObject

data class PasswordHash(val value: String) : ValueObject() {
    companion object {
        fun create(value: String): PasswordHash = PasswordHash(value = value)

        private const val PATTERN = "^.+$"
    }

    init {
        validate()
    }

    private fun validate() {
        require((PATTERN.toRegex().matches(value))) { "PasswordHash's pattern is invalid." }
    }
}
