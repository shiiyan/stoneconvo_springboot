package com.stoneconvo.application.command

import com.stoneconvo.domain.message.MessageContent
import com.stoneconvo.domain.message.MessageId

data class EditMessageCommand(
    val messageId: MessageId,
    val newContent: MessageContent
)
