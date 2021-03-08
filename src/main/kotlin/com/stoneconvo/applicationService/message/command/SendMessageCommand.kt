package com.stoneconvo.applicationService.message.command

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.message.MessageContent
import com.stoneconvo.domain.userAccount.UserAccountId

data class SendMessageCommand(
    val messageContent: MessageContent,
    val roomId: ChatRoomId,
    val currentUserId: UserAccountId
)
