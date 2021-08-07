package com.stoneconvo.application.userAccount.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.userAccount.PasswordHash
import com.stoneconvo.domain.userAccount.UserAccountName

data class CreateAccountCommand(
    val currentUserId: UserAccountId,
    val name: UserAccountName,
    val passwordHash: PasswordHash
) {
    companion object {
        fun create(
            currentUserId: String,
            name: String,
            password: String
        ) = CreateAccountCommand(
            currentUserId = UserAccountId(currentUserId),
            name = UserAccountName(name),
            passwordHash = PasswordHash(Helper.generateHash(password))
        )
    }
}
