package com.stoneconvo.userAccount.applicationService.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.userAccount.domain.PasswordHash
import com.stoneconvo.userAccount.domain.UserAccountName

data class CreateCommand(
    val currentUserId: UserAccountId,
    val name: UserAccountName,
    val passwordHash: PasswordHash
)
