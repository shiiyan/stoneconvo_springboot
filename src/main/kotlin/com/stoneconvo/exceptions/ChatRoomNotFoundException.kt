package com.stoneconvo.exceptions

import com.stoneconvo.domain.chatroom.ChatRoomId

class ChatRoomNotFoundException(chatRoomId: ChatRoomId) :
    Exception("Chat Room Not Found - ChatRoomId: $chatRoomId")
