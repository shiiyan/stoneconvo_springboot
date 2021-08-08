package com.stoneconvo.application

import com.stoneconvo.application.command.chatRoom.AddMemberCommand
import com.stoneconvo.application.command.chatRoom.ChangeMemberNameCommand
import com.stoneconvo.application.command.chatRoom.ChangeRoomNameCommand
import com.stoneconvo.application.command.chatRoom.CreateRoomCommand
import com.stoneconvo.application.command.chatRoom.RemoveMemberCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.administrator.Administrator
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

    @Transactional
    fun handleAddMember(addMemberCommand: AddMemberCommand) {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(addMemberCommand.chatRoomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = addMemberCommand.chatRoomId
                )
        )

        foundChatRoom.verifyAuthorityToAddMember(currentUserId = addMemberCommand.currentUserId)

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

        chatRoomRepository.update(foundChatRoom)
    }

    @Transactional
    fun handleRemoveMember(removeMemberCommand: RemoveMemberCommand) {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(removeMemberCommand.chatRoomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = removeMemberCommand.chatRoomId
                )
        )

        when {
            foundChatRoom.isOwner(removeMemberCommand.currentUserId) -> {
                foundChatRoom.removeMember(removeMemberCommand.userAccountId)
            }
            foundChatRoom.isMemberExist(removeMemberCommand.currentUserId) -> {
                foundChatRoom.removeMember(removeMemberCommand.currentUserId)
            }
            else -> {
                throw CustomException.RemoveMemberUnauthorizedException(
                    userAccountId = removeMemberCommand.currentUserId,
                    chatRoomId = removeMemberCommand.chatRoomId
                )
            }
        }

        chatRoomRepository.update(foundChatRoom)
    }

    @Transactional
    fun handleChangeMemberName(changeMemberNameCommand: ChangeMemberNameCommand) {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(changeMemberNameCommand.chatRoomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = changeMemberNameCommand.chatRoomId
                )
        )

        when {
            foundChatRoom.isOwner(changeMemberNameCommand.currentUserId) -> {
                foundChatRoom.changeMemberName(
                    RoomMember(
                        name = changeMemberNameCommand.newName,
                        userAccountId = changeMemberNameCommand.userAccountId
                    )
                )
            }
            foundChatRoom.isMemberExist(changeMemberNameCommand.currentUserId) -> {
                foundChatRoom.changeMemberName(
                    RoomMember(
                        name = changeMemberNameCommand.newName,
                        userAccountId = changeMemberNameCommand.currentUserId
                    )
                )
            }
            else -> {
                throw CustomException.ChangeMemberNameUnauthorizedException(
                    userAccountId = changeMemberNameCommand.currentUserId,
                    chatRoomId = changeMemberNameCommand.chatRoomId
                )
            }
        }

        chatRoomRepository.update(foundChatRoom)
    }
}
