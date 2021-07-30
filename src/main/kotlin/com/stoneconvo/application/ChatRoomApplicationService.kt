package com.stoneconvo.application

import com.stoneconvo.application.command.AddMemberCommand
import com.stoneconvo.application.command.ChangeMemberNameCommand
import com.stoneconvo.application.command.ChangeRoomNameCommand
import com.stoneconvo.application.command.CreateRoomCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.administrator.AdministratorRepository
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import com.stoneconvo.domain.chatRoom.roomMember.RoomMember
import com.stoneconvo.domain.userAccount.UserAccountRepository
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
    fun handleCreate(createRoomCommand: CreateRoomCommand): String {
        val administrator = administratorRepository.findByUserId(createRoomCommand.currentUserId)
            ?: throw CustomException.AdministratorNotFoundException(
                userId = createRoomCommand.currentUserId
            )

        val newChatRoom = ChatRoom.create(
            name = createRoomCommand.name,
            owner = administrator
        )

        chatRoomRepository.save(newChatRoom)

        return newChatRoom.id.value
    }

    @Transactional
    fun handleChangeName(changeRoomNameCommand: ChangeRoomNameCommand) {
        val foundChatRoom = chatRoomRepository.findByRoomId(changeRoomNameCommand.chatRoomId)
            ?: throw CustomException.ChatRoomNotFoundException(
                chatRoomId = changeRoomNameCommand.chatRoomId
            )

        foundChatRoom.changeName(
            newName = changeRoomNameCommand.newName,
            currentUserId = changeRoomNameCommand.currentUserId
        )

        chatRoomRepository.save(foundChatRoom)
    }

    @Transactional
    fun handleAddMember(addMemberCommand: AddMemberCommand) {
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
    fun handleRemoveMember() {
        TODO()
    }

    @Transactional
    fun handleChangeMemberName(changeMemberNameCommand: ChangeMemberNameCommand) {
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
