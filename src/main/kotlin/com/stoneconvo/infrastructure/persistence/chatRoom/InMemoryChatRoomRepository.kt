package com.stoneconvo.infrastructure.persistence.chatRoom

import com.stoneconvo.common.persistence.InMemoryBaseRepository
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("test")
class InMemoryChatRoomRepository : ChatRoomRepository {
    private val repository: InMemoryBaseRepository<ChatRoom.Dto> = InMemoryBaseRepository()

    fun reset() {
        repository.reset()
    }

    fun count() = repository.count()

    override fun findByRoomId(roomId: ChatRoomId): ChatRoom.Dto? =
        repository.findBy { it.id == roomId.value }

    override fun insert(chatRoom: ChatRoom) {
        repository.insert(chatRoom.toDto())
    }

    override fun update(chatRoom: ChatRoom) {
        repository.update(chatRoom.toDto())
    }
}
