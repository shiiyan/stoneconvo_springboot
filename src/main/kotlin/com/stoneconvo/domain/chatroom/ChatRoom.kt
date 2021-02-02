package com.stoneconvo.domain.chatroom

import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.chatroom.roomMember.RoomMember
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.exceptions.ChatRoomMemberExistException
import com.stoneconvo.exceptions.ChatRoomMemberFullException
import com.stoneconvo.exceptions.ChatRoomMemberNotExistException

class ChatRoom(
    val id: ChatRoomId,
    var name: ChatRoomName,
    val owner: Administrator,
    val members: MutableList<RoomMember>,
    val messages: MutableList<MessageId>
) {
    companion object {
        fun create(
            name: ChatRoomName,
            owner: Administrator
        ) = ChatRoom(
            id = ChatRoomId.create(),
            name = name,
            owner = owner,
            members = mutableListOf(),
            messages = mutableListOf()
        )
    }

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
                userAccountId = newMember.userAccountId
            )
        }

        members.add(newMember)
    }

    fun removeMember(memberToRemove: RoomMember) {
        if (!isMemberExist(memberToRemove)) {
            throw ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = memberToRemove.userAccountId
            )
        }

        members.remove(memberToRemove)
    }

    fun changeMemberName(memberWithNewName: RoomMember) {
        if (!isMemberExist(memberWithNewName)) {
            throw ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = memberWithNewName.userAccountId
            )
        }

        members.replaceAll { m ->
            if
            (m.userAccountId.equals(memberWithNewName.userAccountId)) memberWithNewName
            else m
        }
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
