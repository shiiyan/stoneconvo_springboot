package com.stoneconvo.message.domain

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.userAccount.domain.UserAccountId
import java.time.LocalDateTime

class Message(
    val id: MessageId,
    var content: MessageContent,
    val roomId: ChatRoomId,
    val senderId: UserAccountId,
    val sentDateTime: LocalDateTime,
) {
    companion object {
        fun create(
            content: MessageContent,
            roomId: ChatRoomId,
            senderId: UserAccountId
        ) = Message(
            id = MessageId.create(),
            content = content,
            roomId = roomId,
            senderId = senderId,
            sentDateTime = LocalDateTime.now()
        )
    }

    fun updateContent(newMessageContent: MessageContent) {
        content = newMessageContent
    }
}
