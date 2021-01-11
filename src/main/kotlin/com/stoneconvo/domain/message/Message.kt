package com.stoneconvo.domain.message

import com.stoneconvo.domain.userAccount.UserAccountId
import java.time.LocalDateTime

class Message(
    val id: MessageId,
    var content: MessageContent,
    val sentDateTime: LocalDateTime,
    val sender: UserAccountId
) {
    fun updateContent(newMessageContent: MessageContent) {
        content = newMessageContent
    }
}
