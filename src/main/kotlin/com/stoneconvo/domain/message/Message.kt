package com.stoneconvo.domain.message

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.userAccount.UserAccountId
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
