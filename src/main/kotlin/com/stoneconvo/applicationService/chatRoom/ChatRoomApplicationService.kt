package com.stoneconvo.applicationService.chatRoom

import com.stoneconvo.applicationService.chatRoom.command.AddMemberCommand
import com.stoneconvo.applicationService.chatRoom.command.ChangeMemberNameCommand
import com.stoneconvo.applicationService.chatRoom.command.ChangeNameCommand
import com.stoneconvo.applicationService.chatRoom.command.CreateCommand
import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.roomMember.RoomMember
import com.stoneconvo.exceptions.AdministratorNotFoundException
import com.stoneconvo.exceptions.ChatRoomNotFoundException
import com.stoneconvo.repository.administrator.AdministratorRepository
import com.stoneconvo.repository.chatRoom.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChatRoomApplicationService(
    @Autowired
    private val administratorRepository: AdministratorRepository,
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
) {
    fun create(createCommand: CreateCommand) {
        val administrator = administratorRepository.findByUserId(createCommand.currentUserId)
            ?: throw AdministratorNotFoundException(
                userId = createCommand.currentUserId
            )

        val newChatRoom = ChatRoom.create(
            name = createCommand.name,
            owner = administrator
        )

        chatRoomRepository.save(newChatRoom)
    }

    fun changeName(changeNameCommand: ChangeNameCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(changeNameCommand.chatRoomId)
            ?: throw ChatRoomNotFoundException(
                chatRoomId = changeNameCommand.chatRoomId
            )

        foundChatRoom.changeName(changeNameCommand.newName)

        chatRoomRepository.save(foundChatRoom)
    }

    fun addMember(addMemberCommand: AddMemberCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(addMemberCommand.chatRoomId)
            ?: throw ChatRoomNotFoundException(
                chatRoomId = addMemberCommand.chatRoomId
            )

        foundChatRoom.addMember(
            RoomMember(
                name = addMemberCommand.name,
                userAccountId = addMemberCommand.userAccountId
            )
        )

        chatRoomRepository.save(foundChatRoom)
    }

    fun changeMemberName(changeMemberNameCommand: ChangeMemberNameCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(changeMemberNameCommand.chatRoomId)
            ?: throw ChatRoomNotFoundException(
                chatRoomId = changeMemberNameCommand.chatRoomId
            )

        foundChatRoom.changeMemberName(
            RoomMember(
                name = changeMemberNameCommand.newName,
                userAccountId = changeMemberNameCommand.userAccountId
            )
        )

        chatRoomRepository.save(foundChatRoom)
    }
}
