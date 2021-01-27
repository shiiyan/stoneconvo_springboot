package com.stoneconvo.applicationService.chatRoom

import com.stoneconvo.applicationService.chatRoom.command.AddMemberCommand
import com.stoneconvo.applicationService.chatRoom.command.ChangeNameCommand
import com.stoneconvo.applicationService.chatRoom.command.CreateCommand
import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.exceptions.AdministratorNotFoundException
import com.stoneconvo.exceptions.ChatRoomNotFoundException
import com.stoneconvo.exceptions.RoomMemberNotFoundException
import com.stoneconvo.repository.administrator.AdministratorRepository
import com.stoneconvo.repository.chatRoom.ChatRoomRepository
import com.stoneconvo.repository.roomMember.RoomMemberRepository

class ChatRoomApplicationService(
    private val administratorRepository: AdministratorRepository,
    private val chatRoomRepository: ChatRoomRepository,
    private val roomMemberRepository: RoomMemberRepository,
) {
    fun create(createCommand: CreateCommand) {
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

    fun changeName(changeNameCommand: ChangeNameCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(changeNameCommand.chatRoomId)
            ?: throw ChatRoomNotFoundException(
                chatRoomId = changeNameCommand.chatRoomId,
                message = "Chat Room Not Found"
            )

        foundChatRoom.changeName(changeNameCommand.newName)

        chatRoomRepository.save(foundChatRoom)
    }

    fun addMember(addMemberCommand: AddMemberCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(addMemberCommand.chatRoomId)
            ?: throw ChatRoomNotFoundException(
                chatRoomId = addMemberCommand.chatRoomId,
                message = "Chat Room Not Found"
            )
        val foundRoomMember = roomMemberRepository.findByRoomMemberId(addMemberCommand.newMemberId)
            ?: throw RoomMemberNotFoundException(
                roomMemberId = addMemberCommand.newMemberId,
                message = "Room Member Not Found"
            )

        foundChatRoom.addMember(foundRoomMember)

        chatRoomRepository.save(foundChatRoom)
    }
}
