package com.stoneconvo.applicationService.chatRoom.command

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.chatroom.ChatRoomName

data class ChangeNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: ChatRoomName
)
