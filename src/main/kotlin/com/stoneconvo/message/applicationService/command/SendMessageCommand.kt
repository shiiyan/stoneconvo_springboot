package com.stoneconvo.message.applicationService.command

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.message.domain.MessageContent
import com.stoneconvo.userAccount.domain.UserAccountId

data class SendMessageCommand(
    val messageContent: MessageContent,
    val roomId: ChatRoomId,
    val currentUserId: UserAccountId
)
