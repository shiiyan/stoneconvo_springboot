package com.stoneconvo.application.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.userAccount.UserAccountName

data class ChangeAccountNameCommand(
    val currentUserId: UserAccountId,
    val newName: UserAccountName
)
