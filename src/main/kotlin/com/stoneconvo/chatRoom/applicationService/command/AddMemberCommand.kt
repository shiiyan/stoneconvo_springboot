package com.stoneconvo.chatRoom.applicationService.command

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.chatRoom.domain.roomMember.RoomMemberName
import com.stoneconvo.common.domain.UserAccountId

data class AddMemberCommand(
    val chatRoomId: ChatRoomId,
    val name: RoomMemberName,
    val userAccountId: UserAccountId
)
