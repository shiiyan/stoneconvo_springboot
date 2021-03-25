package com.stoneconvo.message.applicationService.command

import com.stoneconvo.message.domain.MessageContent
import com.stoneconvo.message.domain.MessageId

data class EditMessageCommand(
    val messageId: MessageId,
    val newContent: MessageContent
)
