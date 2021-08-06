package com.stoneconvo.domain.message

interface MessageRepository {
    fun findByMessageId(messageId: MessageId): Message.Dto?
    fun insert(message: Message)
    fun update(message: Message)
}
