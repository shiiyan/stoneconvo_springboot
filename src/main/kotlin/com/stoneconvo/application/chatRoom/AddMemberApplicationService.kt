package com.stoneconvo.application.chatRoom

import com.stoneconvo.application.chatRoom.command.AddMemberCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import com.stoneconvo.domain.chatRoom.roomMember.RoomMember
import com.stoneconvo.domain.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddMemberApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository,
    @Autowired
    private val userAccountRepository: UserAccountRepository
) {
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
}
