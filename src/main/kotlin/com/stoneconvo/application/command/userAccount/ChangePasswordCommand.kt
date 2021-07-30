package com.stoneconvo.application.command.userAccount

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.userAccount.PasswordHash

data class ChangePasswordCommand(
    val currentUserId: UserAccountId,
    val passwordHash: PasswordHash
) {
    companion object {
        fun create(
            currentUserId: String,
            password: String
        ) = ChangePasswordCommand(
            currentUserId = UserAccountId(currentUserId),
            passwordHash = PasswordHash(Helper.generateHash(password))
        )
    }
}
