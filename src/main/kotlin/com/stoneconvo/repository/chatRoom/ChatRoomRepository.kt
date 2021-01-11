package com.stoneconvo.repository.chatRoom

import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.ChatRoomId

interface ChatRoomRepository {
    fun findByRoomId(roomId: ChatRoomId): ChatRoom?
    fun save(chatRoom: ChatRoom)
}
