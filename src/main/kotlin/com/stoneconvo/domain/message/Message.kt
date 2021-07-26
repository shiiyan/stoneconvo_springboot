package com.stoneconvo.domain.message

import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import java.time.LocalDateTime

class Message(
    val id: MessageId,
    var content: MessageContent,
    val roomId: ChatRoomId,
    val senderId: UserAccountId,
    val sentDateTime: LocalDateTime,
) : Entity() {
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