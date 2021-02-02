package com.stoneconvo.repository.chatRoom

import com.stoneconvo.codegen.tables.daos.JChatRoomsDao
import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.ChatRoomId
import org.springframework.stereotype.Repository

@Repository
class JooqChatRoomRepository : ChatRoomRepository {
    private val dao = JChatRoomsDao()

    override fun findByRoomId(roomId: ChatRoomId): ChatRoom? {
        TODO("Not yet implemented")
    }

    override fun save(chatRoom: ChatRoom) {
        TODO("Not yet implemented")
    }
}
