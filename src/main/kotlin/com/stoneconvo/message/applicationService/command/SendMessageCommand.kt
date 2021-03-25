package com.stoneconvo.message.applicationService.command

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.message.domain.MessageContent

data class SendMessageCommand(
    val messageContent: MessageContent,
    val roomId: ChatRoomId,
    val currentUserId: UserAccountId
)
