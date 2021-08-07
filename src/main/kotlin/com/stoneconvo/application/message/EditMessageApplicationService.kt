package com.stoneconvo.application.message

import com.stoneconvo.application.message.command.EditMessageCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EditMessageApplicationService(
    @Autowired
    private val messageRepository: MessageRepository
) {
    @Transactional
    fun handleEdit(editMessageCommand: EditMessageCommand) {
        val foundMessage = Message.fromDto(
            messageRepository.findByMessageId(editMessageCommand.messageId)
                ?: throw CustomException.MessageNotFoundException(
                    messageId = editMessageCommand.messageId
                )
        )

        if (!foundMessage.isSender(editMessageCommand.currentUserId)) {
            throw CustomException.EditMessageUnauthorizedException(
                messageId = editMessageCommand.messageId,
                userAccountId = editMessageCommand.currentUserId
            )
        }

        foundMessage.updateContent(editMessageCommand.newContent)

        messageRepository.update(foundMessage)
    }
}
