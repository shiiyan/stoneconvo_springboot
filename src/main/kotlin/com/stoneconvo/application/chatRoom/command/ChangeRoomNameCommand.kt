package com.stoneconvo.application.chatRoom.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.ChatRoomName

data class ChangeRoomNameCommand(
    val currentUserId: UserAccountId,
    val chatRoomId: ChatRoomId,
    val newName: ChatRoomName
) {
    companion object {
        fun create(
            currentUserId: String,
            chatRoomId: String,
            newName: String
        ) = ChangeRoomNameCommand(
            currentUserId = UserAccountId(currentUserId),
            chatRoomId = ChatRoomId(chatRoomId),
            newName = ChatRoomName(newName)
        )
    }
}
