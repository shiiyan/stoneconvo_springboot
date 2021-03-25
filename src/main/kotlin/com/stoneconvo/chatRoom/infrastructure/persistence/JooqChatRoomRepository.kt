package com.stoneconvo.chatRoom.infrastructure.persistence

import com.stoneconvo.administrator.domain.Administrator
import com.stoneconvo.chatRoom.domain.ChatRoom
import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.chatRoom.domain.ChatRoomName
import com.stoneconvo.chatRoom.domain.ChatRoomRepository
import com.stoneconvo.chatRoom.domain.roomMember.RoomMember
import com.stoneconvo.chatRoom.domain.roomMember.RoomMemberName
import com.stoneconvo.codegen.tables.daos.JChatRoomsDao
import com.stoneconvo.codegen.tables.daos.JRoomMembersDao
import com.stoneconvo.codegen.tables.pojos.JChatRooms
import com.stoneconvo.codegen.tables.pojos.JRoomMembers
import com.stoneconvo.userAccount.domain.UserAccountId
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class JooqChatRoomRepository(
    @Autowired
    private val dslContext: DSLContext
) : ChatRoomRepository {
    private val chatRoomDao = JChatRoomsDao(dslContext.configuration())
    private val roomMembersDao = JRoomMembersDao()

    override fun findByRoomId(roomId: ChatRoomId): ChatRoom? =
        chatRoomDao.fetchOneByJRoomId(roomId.value)?.let {
            ChatRoom(
                id = ChatRoomId(it.roomId),
                name = ChatRoomName(it.roomName),
                owner = Administrator(UserAccountId(it.roomOwnerId)),
                members = fetchRoomMembersByRoomId(roomId)
            )
        }

    override fun save(chatRoom: ChatRoom) {
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
}
