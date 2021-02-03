package com.stoneconvo.applicationService.chatRoom.command

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.chatroom.roomMember.RoomMemberName
import com.stoneconvo.domain.userAccount.UserAccountId

data class ChangeMemberNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: RoomMemberName,
    val userAccountId: UserAccountId
)
