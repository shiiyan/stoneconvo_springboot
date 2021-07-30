package com.stoneconvo.application.command.message

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.message.MessageContent

data class SendMessageCommand(
    val messageContent: MessageContent,
    val roomId: ChatRoomId,
    val currentUserId: UserAccountId
)
