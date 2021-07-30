package com.stoneconvo.application.command.chatRoom

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName

data class ChangeMemberNameCommand(
    val chatRoomId: ChatRoomId,
    val newName: RoomMemberName,
    val userAccountId: UserAccountId,
    val currentUserId: UserAccountId
) {
    companion object {
        fun create(
            chatRoomId: String,
            name: String,
            userAccountId: String,
            currentUserId: String
        ) = ChangeMemberNameCommand(
            chatRoomId = ChatRoomId(chatRoomId),
            newName = RoomMemberName(name),
            userAccountId = UserAccountId(userAccountId),
            currentUserId = UserAccountId(currentUserId)
        )
    }
}
