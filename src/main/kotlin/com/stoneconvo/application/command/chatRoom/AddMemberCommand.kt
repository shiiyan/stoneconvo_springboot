package com.stoneconvo.application.command.chatRoom

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName

data class AddMemberCommand(
    val chatRoomId: ChatRoomId,
    val name: RoomMemberName,
    val userAccountId: UserAccountId,
    val currentUserId: UserAccountId
) {
    companion object {
        fun create(
            chatRoomId: String,
            name: String,
            userAccountId: String,
            currentUserId: String
        ) = AddMemberCommand(
            chatRoomId = ChatRoomId(chatRoomId),
            name = RoomMemberName(name),
            userAccountId = UserAccountId(userAccountId),
            currentUserId = UserAccountId(currentUserId)
        )
    }
}
