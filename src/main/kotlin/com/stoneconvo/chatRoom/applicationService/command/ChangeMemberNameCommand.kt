package com.stoneconvo.chatRoom.applicationService.command

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.chatRoom.domain.roomMember.RoomMemberName
import com.stoneconvo.userAccount.domain.UserAccountId

data class ChangeMemberNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: RoomMemberName,
    val userAccountId: UserAccountId
)
