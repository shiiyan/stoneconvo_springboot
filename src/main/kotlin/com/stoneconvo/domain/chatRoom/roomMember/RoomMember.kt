package com.stoneconvo.domain.chatRoom.roomMember

import com.stoneconvo.common.domain.UserAccountId

data class RoomMember(
    var name: RoomMemberName,
    val userAccountId: UserAccountId,
)
