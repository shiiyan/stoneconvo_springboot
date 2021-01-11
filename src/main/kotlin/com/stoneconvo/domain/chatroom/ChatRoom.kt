package com.stoneconvo.domain.chatroom

import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.domain.roomMember.RoomMember

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
}
