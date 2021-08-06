package com.stoneconvo.domain.chatRoom.roomMember

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.domain.ValueObject

data class RoomMember(
    var name: RoomMemberName,
    val userAccountId: UserAccountId,
) : ValueObject() {
    companion object {
        fun fromDto(dto: Dto) = RoomMember(
            name = RoomMemberName(dto.name),
            userAccountId = UserAccountId(dto.userAccountId)
        )
    }

    fun toDto(): Dto = Dto(
        name = name.value,
        userAccountId = userAccountId.value
    )

    data class Dto(
        val name: String,
        val userAccountId: String
    )
}
