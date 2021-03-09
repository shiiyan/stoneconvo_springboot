package com.stoneconvo.applicationService.message

import com.stoneconvo.applicationService.message.command.EditMessageCommand
import com.stoneconvo.applicationService.message.command.SendMessageCommand
import com.stoneconvo.domain.message.Message
import com.stoneconvo.exceptions.ChatRoomMemberNotExistException
import com.stoneconvo.exceptions.ChatRoomNotFoundException
import com.stoneconvo.exceptions.MessageNotFoundException
import com.stoneconvo.repository.chatRoom.ChatRoomRepository
import com.stoneconvo.repository.message.MessageRepository
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
            ?: throw ChatRoomNotFoundException(
                chatRoomId = sendMessageCommand.roomId
            )

        if (!foundChatRoom.isMemberExist(sendMessageCommand.currentUserId)) {
            throw ChatRoomMemberNotExistException(
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
            ?: throw MessageNotFoundException(
                messageId = editMessageCommand.messageId
            )

        foundMessage.updateContent(editMessageCommand.newContent)

        messageRepository.update(foundMessage)
    }
}
