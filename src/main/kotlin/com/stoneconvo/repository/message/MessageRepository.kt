package com.stoneconvo.repository.message

import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageId

interface MessageRepository {
    fun findByMessageId(messageId: MessageId): Message?
    fun insert(message: Message)
    fun update(message: Message)
}
