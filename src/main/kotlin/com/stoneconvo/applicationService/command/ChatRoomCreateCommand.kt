package com.stoneconvo.applicationService.command

import com.stoneconvo.domain.chatroom.ChatRoomName
import com.stoneconvo.domain.userAccount.UserAccountId

class ChatRoomCreateCommand(
    val currentUserId: UserAccountId,
    val name: ChatRoomName,
)
