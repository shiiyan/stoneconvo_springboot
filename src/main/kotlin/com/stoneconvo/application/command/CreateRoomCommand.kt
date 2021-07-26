package com.stoneconvo.application.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomName

data class CreateRoomCommand(
    val currentUserId: UserAccountId,
    val name: ChatRoomName,
)
