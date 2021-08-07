package com.stoneconvo.application.message

import com.stoneconvo.application.message.command.SendMessageCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SendMessageApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
    @Autowired
    private val messageRepository: MessageRepository
) {
    @Transactional
    fun handleSend(sendMessageCommand: SendMessageCommand): String {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(sendMessageCommand.roomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = sendMessageCommand.roomId
                )
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

        return newMessage.id.value
    }
}
