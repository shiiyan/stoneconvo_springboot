package com.stoneconvo.repository.roomMember

import com.stoneconvo.domain.roomMember.RoomMember
import com.stoneconvo.domain.roomMember.RoomMemberId

interface RoomMemberRepository {
    fun findByRoomMemberId(roomMemberId: RoomMemberId): RoomMember?
    fun save(roomMember: RoomMember)
}
