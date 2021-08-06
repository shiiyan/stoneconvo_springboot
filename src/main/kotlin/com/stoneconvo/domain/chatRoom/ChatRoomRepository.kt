package com.stoneconvo.domain.chatRoom

interface ChatRoomRepository {
    fun findByRoomId(roomId: ChatRoomId): ChatRoom.Dto?
    fun insert(chatRoom: ChatRoom)
    fun update(chatRoom: ChatRoom)
}
