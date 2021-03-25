package com.stoneconvo.chatRoom.domain

interface ChatRoomRepository {
    fun findByRoomId(roomId: ChatRoomId): ChatRoom?
    fun save(chatRoom: ChatRoom)
}
