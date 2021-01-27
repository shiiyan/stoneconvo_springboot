package com.stoneconvo.exceptions

import com.stoneconvo.domain.chatroom.ChatRoomId

class ChatRoomNotFoundException(chatRoomId: ChatRoomId, message: String) :
    Exception("$message - ChatRoomId: $chatRoomId")
