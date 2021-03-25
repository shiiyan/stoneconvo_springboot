package com.stoneconvo.userAccount.domain

import com.stoneconvo.common.domain.ValueObject

data class PasswordHash(val value: String) : ValueObject() {
    companion object {
        fun create(plainPassword: String): PasswordHash = PasswordHash(
            // TODO: add hash function here
            value = plainPassword
        )

        private const val PATTERN = "^.+$"
    }

    init {
        validate()
    }

    fun isSameWith(another: PasswordHash) = this == another

    // TODO: fix regex after implement password hash
    private fun validate() {
        require((PATTERN.toRegex().matches(value))) { "PasswordHash's pattern is invalid." }
    }
}
