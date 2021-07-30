package com.stoneconvo.application.command.userAccount

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.userAccount.UserAccountName

data class ChangeAccountNameCommand(
    val currentUserId: UserAccountId,
    val newName: UserAccountName
) {
    companion object {
        fun create(
            currentUserId: String,
            newName: String
        ) = ChangeAccountNameCommand(
            currentUserId = UserAccountId(currentUserId),
            newName = UserAccountName(newName)
        )
    }
}
