package com.stoneconvo.chatRoom.applicationService.command

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.chatRoom.domain.ChatRoomName

data class ChangeNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: ChatRoomName
)
