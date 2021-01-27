package com.stoneconvo.exceptions

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.roomMember.RoomMemberId

class ChatRoomMemberExistException(chatRoomId: ChatRoomId, roomMemberId: RoomMemberId) :
    Exception("Chat Room Member Exists - ChatRoomId: $chatRoomId -MemberId: $roomMemberId")
