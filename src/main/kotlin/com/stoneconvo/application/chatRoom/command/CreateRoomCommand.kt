package com.stoneconvo.application.chatRoom.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomName

data class CreateRoomCommand(
    val currentUserId: UserAccountId,
    val name: ChatRoomName,
) {
    companion object {
        fun create(
            currentUserId: String,
            name: String
        ) = CreateRoomCommand(
            currentUserId = UserAccountId(currentUserId),
            name = ChatRoomName(name)
        )
    }
}
