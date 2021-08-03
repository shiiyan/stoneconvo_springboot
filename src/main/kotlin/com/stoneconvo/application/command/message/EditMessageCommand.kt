package com.stoneconvo.application.command.message

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.message.MessageContent
import com.stoneconvo.domain.message.MessageId

data class EditMessageCommand(
    val messageId: MessageId,
    val newContent: MessageContent,
    val currentUserId: UserAccountId
) {
    companion object {
        fun create(
            messageId: String,
            newContent: String,
            currentUserId: String
        ) = EditMessageCommand(
            messageId = MessageId(messageId),
            newContent = MessageContent(newContent),
            currentUserId = UserAccountId(currentUserId)
        )
    }
}
