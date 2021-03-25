package com.stoneconvo.chatRoom.domain.roomMember

import com.stoneconvo.common.domain.UserAccountId

data class RoomMember(
    var name: RoomMemberName,
    val userAccountId: UserAccountId,
)
