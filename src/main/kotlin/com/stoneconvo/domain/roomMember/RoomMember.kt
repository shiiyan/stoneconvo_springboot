package com.stoneconvo.domain.roomMember

import com.stoneconvo.domain.userAccount.UserAccountId

class RoomMember(
    val id: UserAccountId,
    var name: RoomMemberName
) {
    fun changeName(newName: RoomMemberName) {
        name = newName
    }
}
