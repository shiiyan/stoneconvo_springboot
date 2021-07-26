package com.stoneconvo.application.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName

data class ChangeMemberNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: RoomMemberName,
    val userAccountId: UserAccountId
)
