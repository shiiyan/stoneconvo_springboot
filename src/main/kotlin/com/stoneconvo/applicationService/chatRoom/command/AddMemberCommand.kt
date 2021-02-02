package com.stoneconvo.applicationService.chatRoom.command

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.chatroom.roomMember.RoomMemberName
import com.stoneconvo.domain.userAccount.UserAccountId

data class AddMemberCommand(
    val chatRoomId: ChatRoomId,
    val name: RoomMemberName,
    val userAccountId: UserAccountId
)
