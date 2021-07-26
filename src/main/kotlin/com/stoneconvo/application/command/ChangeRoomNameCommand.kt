package com.stoneconvo.application.command

import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.ChatRoomName

data class ChangeRoomNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: ChatRoomName
)
