package com.stoneconvo.application.chatRoom

import com.stoneconvo.application.chatRoom.command.RemoveMemberCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RemoveMemberApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional
    fun handleRemoveMember(removeMemberCommand: RemoveMemberCommand) {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(removeMemberCommand.chatRoomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = removeMemberCommand.chatRoomId
                )
        )

        if (!foundChatRoom.verifyAuthorityToEditMember(
                memberIdToEdit = removeMemberCommand.userAccountId,
                currentUserId = removeMemberCommand.currentUserId
            )
        ) {
            throw CustomException.RemoveMemberUnauthorizedException(
                userAccountId = removeMemberCommand.currentUserId,
                chatRoomId = removeMemberCommand.chatRoomId
            )
        }

        foundChatRoom.removeMember(removeMemberCommand.userAccountId)

        chatRoomRepository.update(foundChatRoom)
    }
}
