package com.stoneconvo.domain.roomMember

import com.stoneconvo.domain.userAccount.UserAccountId

class RoomMember(
    val id: RoomMemberId,
    var name: RoomMemberName,
    val userAccountId: UserAccountId,
) {
    fun changeName(newName: RoomMemberName) {
        name = newName
    }
}
