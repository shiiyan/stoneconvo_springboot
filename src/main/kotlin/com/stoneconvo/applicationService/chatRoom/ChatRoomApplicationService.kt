package com.stoneconvo.applicationService.chatRoom

import com.stoneconvo.applicationService.chatRoom.command.AddMemberCommand
import com.stoneconvo.applicationService.chatRoom.command.ChangeMemberNameCommand
import com.stoneconvo.applicationService.chatRoom.command.ChangeNameCommand
import com.stoneconvo.applicationService.chatRoom.command.CreateCommand
import com.stoneconvo.domain.chatroom.ChatRoom
import com.stoneconvo.domain.chatroom.roomMember.RoomMember
import com.stoneconvo.exception.CustomException
import com.stoneconvo.repository.administrator.AdministratorRepository
import com.stoneconvo.repository.chatRoom.ChatRoomRepository
import com.stoneconvo.repository.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomApplicationService(
    @Autowired
    private val administratorRepository: AdministratorRepository,
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
    @Autowired
    private val userAccountRepository: UserAccountRepository,
) {
    @Transactional
    fun create(createCommand: CreateCommand) {
        val administrator = administratorRepository.findByUserId(createCommand.currentUserId)
            ?: throw CustomException.AdministratorNotFoundException(
                userId = createCommand.currentUserId
            )

        val newChatRoom = ChatRoom.create(
            name = createCommand.name,
            owner = administrator
        )

        chatRoomRepository.save(newChatRoom)
    }

    @Transactional
    fun changeName(changeNameCommand: ChangeNameCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(changeNameCommand.chatRoomId)
            ?: throw CustomException.ChatRoomNotFoundException(
                chatRoomId = changeNameCommand.chatRoomId
            )

        foundChatRoom.changeName(changeNameCommand.newName)

        chatRoomRepository.save(foundChatRoom)
    }

    @Transactional
    fun addMember(addMemberCommand: AddMemberCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(addMemberCommand.chatRoomId)
            ?: throw CustomException.ChatRoomNotFoundException(
                chatRoomId = addMemberCommand.chatRoomId
            )

        if (userAccountRepository.findByUserId(addMemberCommand.userAccountId) == null) {
            throw CustomException.UserAccountNotFoundException(
                userId = addMemberCommand.userAccountId.value
            )
        }

        foundChatRoom.addMember(
            RoomMember(
                name = addMemberCommand.name,
                userAccountId = addMemberCommand.userAccountId
            )
        )

        chatRoomRepository.save(foundChatRoom)
    }

    @Transactional
    fun removeMember() {
        TODO()
    }

    @Transactional
    fun changeMemberName(changeMemberNameCommand: ChangeMemberNameCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(changeMemberNameCommand.chatRoomId)
            ?: throw CustomException.ChatRoomNotFoundException(
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
