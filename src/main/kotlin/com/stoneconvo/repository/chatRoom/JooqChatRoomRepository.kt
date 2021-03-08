package com.stoneconvo.repository.chatRoom

import com.stoneconvo.codegen.tables.daos.JChatRoomsDao
import com.stoneconvo.codegen.tables.daos.JRoomMembersDao
import com.stoneconvo.codegen.tables.pojos.JChatRooms
import com.stoneconvo.codegen.tables.pojos.JRoomMembers
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.chatroom.ChatRoomName
import com.stoneconvo.domain.chatroom.roomMember.RoomMember
import com.stoneconvo.domain.chatroom.roomMember.RoomMemberName
import com.stoneconvo.domain.userAccount.UserAccountId
import org.springframework.stereotype.Repository

@Repository
class JooqChatRoomRepository : ChatRoomRepository {
    private val chatRoomDao = JChatRoomsDao()
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
