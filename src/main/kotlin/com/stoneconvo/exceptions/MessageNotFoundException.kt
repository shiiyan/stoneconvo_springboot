package com.stoneconvo.exceptions

import com.stoneconvo.domain.message.MessageId

class MessageNotFoundException(messageId: MessageId) :
    Exception("Message Not Found - MessageId: $messageId")
