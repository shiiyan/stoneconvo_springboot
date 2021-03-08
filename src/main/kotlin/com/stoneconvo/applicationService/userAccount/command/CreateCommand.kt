package com.stoneconvo.applicationService.userAccount.command

import com.stoneconvo.domain.userAccount.PasswordHash
import com.stoneconvo.domain.userAccount.UserAccountId
import com.stoneconvo.domain.userAccount.UserAccountName

data class CreateCommand(
    val currentUserId: UserAccountId,
    val name: UserAccountName,
    val passwordHash: PasswordHash
)
