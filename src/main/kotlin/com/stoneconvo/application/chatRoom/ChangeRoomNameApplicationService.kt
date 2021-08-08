package com.stoneconvo.application.chatRoom

import com.stoneconvo.application.chatRoom.command.ChangeRoomNameCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeRoomNameApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
) {
    @Transactional
    fun handleChangeName(changeRoomNameCommand: ChangeRoomNameCommand) {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(changeRoomNameCommand.chatRoomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = changeRoomNameCommand.chatRoomId
                )
        )

        foundChatRoom.changeName(
            newName = changeRoomNameCommand.newName,
            currentUserId = changeRoomNameCommand.currentUserId
        )

        chatRoomRepository.update(foundChatRoom)
    }
}
