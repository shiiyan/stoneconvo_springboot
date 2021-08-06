package com.stoneconvo.domain.message

import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import java.time.LocalDateTime

class Message(
    override val id: MessageId,
    var content: MessageContent,
    val roomId: ChatRoomId,
    val senderId: UserAccountId,
    val sentDateTime: LocalDateTime,
) : Entity<Message.Dto>(id) {
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

        fun fromDto(dto: Dto) = Message(
            id = MessageId(dto.id),
            content = MessageContent(dto.content),
            roomId = ChatRoomId(dto.roomId),
            senderId = UserAccountId(dto.senderId),
            sentDateTime = dto.sentDateTime
        )
    }

    fun isSender(userAccountId: UserAccountId): Boolean = senderId == userAccountId

    fun updateContent(newMessageContent: MessageContent) {
        content = newMessageContent
    }

    override fun toDto(): Dto = Dto(
        id = id.value,
        content = content.value,
        roomId = roomId.value,
        senderId = senderId.value,
        sentDateTime = sentDateTime
    )

    data class Dto(
        override val id: String,
        val content: String,
        val roomId: String,
        val senderId: String,
        val sentDateTime: LocalDateTime
    ) : Entity.Dto(id)
}
