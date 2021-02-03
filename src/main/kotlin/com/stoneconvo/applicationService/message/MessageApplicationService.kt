package com.stoneconvo.applicationService.message

import com.stoneconvo.applicationService.message.command.SendMessageCommand
import com.stoneconvo.domain.message.Message
import com.stoneconvo.exceptions.ChatRoomMemberNotExistException
import com.stoneconvo.exceptions.ChatRoomNotFoundException
import com.stoneconvo.repository.chatRoom.ChatRoomRepository
import com.stoneconvo.repository.message.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
    @Autowired
    private val messageRepository: MessageRepository
) {
    fun send(sendMessageCommand: SendMessageCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(sendMessageCommand.roomId)
            ?: throw ChatRoomNotFoundException(
                chatRoomId = sendMessageCommand.roomId,
                message = "Chat Room Not Found"
            )

        if (!foundChatRoom.isMemberExist(sendMessageCommand.senderId)) {
            throw ChatRoomMemberNotExistException(
                chatRoomId = sendMessageCommand.roomId,
                userAccountId = sendMessageCommand.senderId
            )
        }

        val newMessage = Message.create(
            content = sendMessageCommand.messageContent,
            roomId = sendMessageCommand.roomId,
            senderId = sendMessageCommand.senderId
        )

        messageRepository.save(newMessage)
    }
}
