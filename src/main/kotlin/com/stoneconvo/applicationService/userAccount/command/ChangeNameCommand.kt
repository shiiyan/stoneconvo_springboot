package com.stoneconvo.applicationService.userAccount.command

import com.stoneconvo.domain.userAccount.UserAccountId
import com.stoneconvo.domain.userAccount.UserAccountName

data class ChangeNameCommand(
    val currentUserId: UserAccountId,
    val newName: UserAccountName
)
