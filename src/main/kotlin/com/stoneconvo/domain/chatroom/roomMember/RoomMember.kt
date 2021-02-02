package com.stoneconvo.domain.chatroom.roomMember

import com.stoneconvo.domain.userAccount.UserAccountId

data class RoomMember(
    var name: RoomMemberName,
    val userAccountId: UserAccountId,
)
