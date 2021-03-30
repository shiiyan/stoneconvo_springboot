package com.stoneconvo.userAccount.applicationService.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.userAccount.domain.PasswordHash
import com.stoneconvo.userAccount.domain.UserAccountName

data class CreateCommand(
    val currentUserId: UserAccountId,
    val name: UserAccountName,
    val passwordHash: PasswordHash
) {
    companion object {
        fun create(
            currentUserId: String,
            name: String,
            password: String
        ) = CreateCommand(
            currentUserId = UserAccountId(currentUserId),
            name = UserAccountName(name),
            passwordHash = PasswordHash(Helper.generateHash(password))
        )
    }
}
