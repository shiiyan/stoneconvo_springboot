package com.stoneconvo.applicationService.message.command

import com.stoneconvo.domain.message.MessageContent
import com.stoneconvo.domain.message.MessageId

data class EditMessageCommand(
    val messageId: MessageId,
    val newContent: MessageContent
)
