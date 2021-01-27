package com.stoneconvo.applicationService.chatRoom.command

import com.stoneconvo.domain.chatroom.ChatRoomName
import com.stoneconvo.domain.userAccount.UserAccountId

data class CreateCommand(
    val currentUserId: UserAccountId,
    val name: ChatRoomName,
)
