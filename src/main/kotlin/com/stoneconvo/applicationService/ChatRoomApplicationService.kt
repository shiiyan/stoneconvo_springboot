package com.stoneconvo.applicationService

import com.stoneconvo.applicationService.command.ChatRoomCreateCommand
import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.exceptions.AdministratorNotFoundException
import com.stoneconvo.repository.administrator.AdministratorRepository
import com.stoneconvo.repository.chatRoom.ChatRoomRepository

class ChatRoomApplicationService(
    private val administratorRepository: AdministratorRepository,
    private val chatRoomRepository: ChatRoomRepository
) {
    fun create(createCommand: ChatRoomCreateCommand) {
        val administrator = administratorRepository.findByUserId(createCommand.currentUserId)
            ?: throw AdministratorNotFoundException(
                userId = createCommand.currentUserId,
                message = "Administrator Not Found"
            )
        val newChatRoom = ChatRoom(
            id = ChatRoomId.create(),
            name = createCommand.name,
            owner = administrator,
            members = mutableListOf(),
            messages = mutableListOf()
        )

        chatRoomRepository.save(newChatRoom)
    }
}
