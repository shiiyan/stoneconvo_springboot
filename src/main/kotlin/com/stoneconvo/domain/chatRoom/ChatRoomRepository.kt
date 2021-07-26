package com.stoneconvo.domain.chatRoom

interface ChatRoomRepository {
    fun findByRoomId(roomId: ChatRoomId): ChatRoom?
    fun save(chatRoom: ChatRoom)
}
