package com.stoneconvo.message.domain

interface MessageRepository {
    fun findByMessageId(messageId: MessageId): Message?
    fun insert(message: Message)
    fun update(message: Message)
}
