package com.stoneconvo.message.applicationService

import com.stoneconvo.chatRoom.domain.ChatRoomRepository
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.message.applicationService.command.EditMessageCommand
import com.stoneconvo.message.applicationService.command.SendMessageCommand
import com.stoneconvo.message.domain.Message
import com.stoneconvo.message.domain.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
    @Autowired
    private val messageRepository: MessageRepository
) {
    @Transactional
    fun send(sendMessageCommand: SendMessageCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(sendMessageCommand.roomId)
            ?: throw CustomException.ChatRoomNotFoundException(
                chatRoomId = sendMessageCommand.roomId
            )

        if (!foundChatRoom.isMemberExist(sendMessageCommand.currentUserId)) {
            throw CustomException.ChatRoomMemberNotExistException(
                chatRoomId = sendMessageCommand.roomId,
                userAccountId = sendMessageCommand.currentUserId
            )
        }

        val newMessage = Message.create(
            content = sendMessageCommand.messageContent,
            roomId = sendMessageCommand.roomId,
            senderId = sendMessageCommand.currentUserId
        )

        messageRepository.insert(newMessage)
    }

    @Transactional
    fun edit(editMessageCommand: EditMessageCommand) {
        val foundMessage = messageRepository.findByMessageId(editMessageCommand.messageId)
            ?: throw CustomException.MessageNotFoundException(
                messageId = editMessageCommand.messageId
            )

        foundMessage.updateContent(editMessageCommand.newContent)

        messageRepository.update(foundMessage)
    }
}
