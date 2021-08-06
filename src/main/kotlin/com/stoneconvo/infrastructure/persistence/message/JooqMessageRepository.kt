package com.stoneconvo.infrastructure.persistence.message

import com.stoneconvo.codegen.tables.daos.JMessagesDao
import com.stoneconvo.codegen.tables.pojos.JMessages
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageContent
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.domain.message.MessageRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("default")
class JooqMessageRepository(
    @Autowired
    private val dslContext: DSLContext
) : MessageRepository {
    private val messageDao = JMessagesDao(dslContext.configuration())

    override fun findByMessageId(messageId: MessageId): Message? =
        messageDao.fetchOneByJMessageId(messageId.value)?.let {
            Message(
                id = MessageId(it.messageId),
                content = MessageContent(it.content),
                roomId = ChatRoomId(it.roomId),
                senderId = UserAccountId(it.senderId),
                sentDateTime = it.sentDateTime
            )
        }

    override fun insert(message: Message) {
        val messagePojo = JMessages()
        messagePojo.messageId = message.id.value
        messagePojo.content = message.content.value
        messagePojo.roomId = message.roomId.value
        messagePojo.senderId = message.senderId.value
        messagePojo.sentDateTime = message.sentDateTime

        messageDao.insert(messagePojo)
    }

    override fun update(message: Message) {
        val messagePojo = JMessages()
        messagePojo.messageId = message.id.value
        messagePojo.content = message.content.value
        messagePojo.roomId = message.roomId.value
        messagePojo.senderId = message.senderId.value
        messagePojo.sentDateTime = message.sentDateTime

        messageDao.update(messagePojo)
    }
}
