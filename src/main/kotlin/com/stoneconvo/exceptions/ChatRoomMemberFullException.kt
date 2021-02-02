package com.stoneconvo.exceptions

import com.stoneconvo.domain.chatroom.ChatRoomId

class ChatRoomMemberFullException(chatRoomId: ChatRoomId) :
    Exception("Chat Room Member Full - ChatRoomId: $chatRoomId")
