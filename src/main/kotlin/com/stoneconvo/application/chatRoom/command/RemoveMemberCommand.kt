package com.stoneconvo.application.chatRoom.command

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId

data class RemoveMemberCommand(
    val chatRoomId: ChatRoomId,
    val userAccountId: UserAccountId,
    val currentUserId: UserAccountId
) {
    companion object {
        fun create(
            chatRoomId: String,
            userAccountId: String,
            currentUserId: String
        ) = RemoveMemberCommand(
            chatRoomId = ChatRoomId(chatRoomId),
            userAccountId = UserAccountId(userAccountId),
            currentUserId = UserAccountId(currentUserId)
        )
    }
}
