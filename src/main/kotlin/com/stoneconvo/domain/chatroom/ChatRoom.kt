package com.stoneconvo.domain.chatroom

import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.chatroom.roomMember.RoomMember
import com.stoneconvo.domain.userAccount.UserAccountId
import com.stoneconvo.exceptions.ChatRoomMemberExistException
import com.stoneconvo.exceptions.ChatRoomMemberFullException
import com.stoneconvo.exceptions.ChatRoomMemberNotExistException

class ChatRoom(
    val id: ChatRoomId,
    var name: ChatRoomName,
    val owner: Administrator,
    val members: MutableList<RoomMember>,
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
        )
    }

    fun changeName(newName: ChatRoomName) {
        name = newName
    }

    fun isMemberExist(userAccountId: UserAccountId): Boolean = members
        .map { it.userAccountId }.contains(userAccountId)

    fun addMember(newMember: RoomMember) {
        if (isMemberFull()) {
            throw ChatRoomMemberFullException(id)
        }

        if (isMemberExist(newMember.userAccountId)) {
            throw ChatRoomMemberExistException(
                chatRoomId = id,
                userAccountId = newMember.userAccountId
            )
        }

        members.add(newMember)
    }

    fun removeMember(memberToRemove: RoomMember) {
        if (!isMemberExist(memberToRemove.userAccountId)) {
            throw ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = memberToRemove.userAccountId
            )
        }

        members.remove(memberToRemove)
    }

    fun changeMemberName(memberWithNewName: RoomMember) {
        if (!isMemberExist(memberWithNewName.userAccountId)) {
            throw ChatRoomMemberNotExistException(
                chatRoomId = id,
                userAccountId = memberWithNewName.userAccountId
            )
        }

        members.replaceAll {
            if (it.userAccountId.equals(memberWithNewName.userAccountId)) memberWithNewName
            else it
        }
    }

    private fun isMemberFull(): Boolean = members.size > 30
}
