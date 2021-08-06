package com.stoneconvo.infrastructure.persistence.message

import com.stoneconvo.common.persistence.InMemoryBaseRepository
import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.domain.message.MessageRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("test")
class InMemoryMessageRepository : MessageRepository {
    private val repository: InMemoryBaseRepository<Message> = InMemoryBaseRepository()

    fun reset() {
        repository.reset()
    }

    fun count() = repository.count()

    override fun findByMessageId(messageId: MessageId): Message? =
        repository.findBy { it.id == messageId }

    override fun insert(message: Message) {
        repository.insert(message)
    }

    override fun update(message: Message) {
        repository.update(message)
    }
}
