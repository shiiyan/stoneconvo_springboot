package com.stoneconvo.repository.message

import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageId
import org.springframework.stereotype.Repository

@Repository
class JooqMessageRepository : MessageRepository {
    override fun findByMessageId(messageId: MessageId): Message? {
        TODO("Not yet implemented")
    }

    override fun save(message: Message) {
        TODO("Not yet implemented")
    }
}
