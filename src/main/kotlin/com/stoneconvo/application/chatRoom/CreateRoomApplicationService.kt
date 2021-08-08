package com.stoneconvo.application.chatRoom

import com.stoneconvo.application.chatRoom.command.CreateRoomCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.administrator.AdministratorRepository
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateRoomApplicationService(
    @Autowired
    private val administratorRepository: AdministratorRepository,
    @Autowired
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional
    fun handleCreate(createRoomCommand: CreateRoomCommand): String {
        val administrator = Administrator.fromDto(
            administratorRepository.findByUserId(createRoomCommand.currentUserId)
                ?: throw CustomException.AdministratorNotFoundException(
                    userId = createRoomCommand.currentUserId
                )
        )

        val newChatRoom = ChatRoom.create(
            name = createRoomCommand.name,
            owner = administrator
        )

        chatRoomRepository.insert(newChatRoom)

        return newChatRoom.id.value
    }
}
