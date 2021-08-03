package com.stoneconvo.application.command.message

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.message.MessageContent

data class SendMessageCommand(
    val messageContent: MessageContent,
    val roomId: ChatRoomId,
    val currentUserId: UserAccountId
) {
    companion object {
        fun create(
            messageContent: String,
            roomId: String,
            currentUserId: String
        ) = SendMessageCommand(
            messageContent = MessageContent(messageContent),
            roomId = ChatRoomId(roomId),
            currentUserId = UserAccountId(currentUserId)
        )
    }
}
