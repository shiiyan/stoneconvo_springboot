package com.stoneconvo.applicationService.chatRoom.command

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.roomMember.RoomMemberId

data class AddMemberCommand(
    val chatRoomId: ChatRoomId,
    val newMemberId: RoomMemberId
)
