package com.stoneconvo.chatRoom.applicationService.command

import com.stoneconvo.chatRoom.domain.ChatRoomName
import com.stoneconvo.userAccount.domain.UserAccountId

data class CreateCommand(
    val currentUserId: UserAccountId,
    val name: ChatRoomName,
)
