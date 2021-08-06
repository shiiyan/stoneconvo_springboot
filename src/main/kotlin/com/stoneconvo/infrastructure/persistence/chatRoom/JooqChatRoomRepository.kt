package com.stoneconvo.infrastructure.persistence.chatRoom

import com.stoneconvo.codegen.tables.daos.JChatRoomsDao
import com.stoneconvo.codegen.tables.daos.JRoomMembersDao
import com.stoneconvo.codegen.tables.pojos.JChatRooms
import com.stoneconvo.codegen.tables.pojos.JRoomMembers
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.ChatRoomName
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import com.stoneconvo.domain.chatRoom.roomMember.RoomMember
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("default")
class JooqChatRoomRepository(
    @Autowired
    private val dslContext: DSLContext
) : ChatRoomRepository {
    private val chatRoomDao = JChatRoomsDao(dslContext.configuration())
    private val roomMembersDao = JRoomMembersDao(dslContext.configuration())

    override fun findByRoomId(roomId: ChatRoomId): ChatRoom.Dto? =
        chatRoomDao.fetchOneByJRoomId(roomId.value)?.let {
            ChatRoom(
                id = ChatRoomId(it.roomId),
                name = ChatRoomName(it.roomName),
                owner = Administrator(UserAccountId(it.roomOwnerId)),
                members = fetchRoomMembersByRoomId(roomId)
            ).toDto()
        }

    override fun insert(chatRoom: ChatRoom) {
        deleteThenInsert(chatRoom)
    }

    override fun update(chatRoom: ChatRoom) {
        deleteThenInsert(chatRoom)
    }

    private fun fetchRoomMembersByRoomId(roomId: ChatRoomId): MutableList<RoomMember> =
        roomMembersDao.fetchByJRoomId(roomId.value).map {
            RoomMember(
                name = RoomMemberName(it.memberName),
                userAccountId = UserAccountId(it.userAccountId)
            )
        }.toMutableList()

    private fun deleteChatRoomByRoomId(roomId: ChatRoomId) {
        val roomPojos = chatRoomDao.fetchByJRoomId(roomId.value)
        chatRoomDao.delete(roomPojos)
    }

    private fun deleteAllRoomMembersByRoomId(roomId: ChatRoomId) {
        val roomMemberPojos = roomMembersDao.fetchByJRoomId(roomId.value)
        roomMembersDao.delete(roomMemberPojos)
    }

    private fun deleteThenInsert(chatRoom: ChatRoom) {
        deleteChatRoomByRoomId(chatRoom.id)

        val chatRoomPojo = JChatRooms()
        chatRoomPojo.roomId = chatRoom.id.value
        chatRoomPojo.roomName = chatRoom.name.value
        chatRoomPojo.roomOwnerId = chatRoom.owner.id.value
        chatRoomDao.insert(chatRoomPojo)

        deleteAllRoomMembersByRoomId(chatRoom.id)

        val roomMemberPojos = chatRoom.members.map {
            val roomMemberPojo = JRoomMembers()
            roomMemberPojo.userAccountId = it.userAccountId.value
            roomMemberPojo.memberName = it.name.value
            roomMemberPojo.roomId = chatRoom.id.value
            roomMemberPojo
        }
        roomMembersDao.insert(roomMemberPojos)
    }
}
