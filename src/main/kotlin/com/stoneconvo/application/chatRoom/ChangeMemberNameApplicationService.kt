package com.stoneconvo.application.chatRoom

import com.stoneconvo.application.chatRoom.command.ChangeMemberNameCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomRepository
import com.stoneconvo.domain.chatRoom.roomMember.RoomMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeMemberNameApplicationService(
    @Autowired
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional
    fun handleChangeMemberName(changeMemberNameCommand: ChangeMemberNameCommand) {
        val foundChatRoom = ChatRoom.fromDto(
            chatRoomRepository.findByRoomId(changeMemberNameCommand.chatRoomId)
                ?: throw CustomException.ChatRoomNotFoundException(
                    chatRoomId = changeMemberNameCommand.chatRoomId
                )
        )

        if (!foundChatRoom.verifyAuthorityToEditMember(
                memberIdToEdit = changeMemberNameCommand.userAccountId,
                currentUserId = changeMemberNameCommand.currentUserId
            )
        ) {
            throw CustomException.ChangeMemberNameUnauthorizedException(
                userAccountId = changeMemberNameCommand.currentUserId,
                chatRoomId = changeMemberNameCommand.chatRoomId
            )
        }

        foundChatRoom.changeMemberName(
            memberWithNewName = RoomMember(
                name = changeMemberNameCommand.newName,
                userAccountId = changeMemberNameCommand.userAccountId
            )
        )

        chatRoomRepository.update(foundChatRoom)
    }
}
