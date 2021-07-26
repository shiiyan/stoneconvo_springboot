package com.stoneconvo.application.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName

data class AddMemberCommand(
    val chatRoomId: ChatRoomId,
    val name: RoomMemberName,
    val userAccountId: UserAccountId
)
