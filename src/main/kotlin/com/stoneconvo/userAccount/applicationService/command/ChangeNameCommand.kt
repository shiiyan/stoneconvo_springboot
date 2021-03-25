package com.stoneconvo.userAccount.applicationService.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.userAccount.domain.UserAccountName

data class ChangeNameCommand(
    val currentUserId: UserAccountId,
    val newName: UserAccountName
)
