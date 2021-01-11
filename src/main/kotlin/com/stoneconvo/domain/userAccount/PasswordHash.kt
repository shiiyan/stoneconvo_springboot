package com.stoneconvo.domain.userAccount

data class PasswordHash(val value: String) {
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
