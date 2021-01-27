package com.stoneconvo.domain.chatroom

import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.domain.roomMember.RoomMember
import com.stoneconvo.exceptions.ChatRoomMemberExistException
import com.stoneconvo.exceptions.ChatRoomMemberFullException

class ChatRoom(
    val id: ChatRoomId,
    var name: ChatRoomName,
    val owner: Administrator,
    val members: MutableList<RoomMember>,
    val messages: MutableList<MessageId>
) {
    fun changeName(newName: ChatRoomName) {
        name = newName
    }

    fun addMember(newMember: RoomMember) {
        if (isMemberFull()) {
            throw ChatRoomMemberFullException(id)
        }

        if (isMemberExist(newMember)) {
            throw ChatRoomMemberExistException(
                chatRoomId = id,
                roomMemberId = newMember.id
            )
        }

        members.add(newMember)
    }

    fun removeMember(memberToRemove: RoomMember) {
        members.remove(memberToRemove)
    }

    fun sendMessage(newMessageId: MessageId) {
        messages.add(newMessageId)
    }

    fun deleteMessage(messageIdToDelete: MessageId) {
        messages.remove(messageIdToDelete)
    }

    private fun isMemberFull(): Boolean = members.size > 30

    private fun isMemberExist(member: RoomMember): Boolean = members.contains(member)
}
