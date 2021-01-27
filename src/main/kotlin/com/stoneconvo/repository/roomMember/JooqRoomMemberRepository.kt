package com.stoneconvo.repository.roomMember

import com.stoneconvo.codegen.tables.daos.JRoomMembersDao
import com.stoneconvo.domain.roomMember.RoomMember
import com.stoneconvo.domain.roomMember.RoomMemberId
import org.springframework.stereotype.Repository

@Repository
class JooqRoomMemberRepository : RoomMemberRepository {
    private val dao = JRoomMembersDao()

    override fun findByRoomMemberId(roomMemberId: RoomMemberId): RoomMember? {
        TODO("Not yet implemented")
    }

    override fun save(roomMember: RoomMember) {
        TODO("Not yet implemented")
    }
}
