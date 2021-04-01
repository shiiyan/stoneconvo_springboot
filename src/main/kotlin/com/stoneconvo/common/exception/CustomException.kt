package com.stoneconvo.common.exception

import com.stoneconvo.chatRoom.domain.ChatRoomId
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.message.domain.MessageId
import java.lang.IllegalStateException

class CustomException {
    open class EntityNotFoundException(errorMessage: String) : IllegalStateException(errorMessage)

    class AdministratorNotFoundException(userId: UserAccountId) :
        EntityNotFoundException("Administrator Not Found - UserId: ${userId.value}")

    class ChatRoomNotFoundException(chatRoomId: ChatRoomId) :
        EntityNotFoundException("Chat Room Not Found - ChatRoomId: ${chatRoomId.value}")

    class MessageNotFoundException(messageId: MessageId) :
        EntityNotFoundException("Message Not Found - MessageId: ${messageId.value}")

    class UserAccountNotFoundException(
        userId: String? = null,
        userName: String? = null
    ) :
        EntityNotFoundException(
            "User Account Not Found - ${
            if (userId != null) "userId: $userId" else ""
            } ${
            if (userName != null) "userName: $userName" else ""
            } "
        )

    open class EntityIllegalStateException(errorMessage: String) : IllegalStateException(errorMessage)

    class ChatRoomMemberExistException(chatRoomId: ChatRoomId, userAccountId: UserAccountId) :
        EntityIllegalStateException(
            "Chat Room Member Exists - ChatRoomId: ${chatRoomId.value} - UserAccountId: ${userAccountId.value}"
        )

    class ChatRoomMemberNotExistException(chatRoomId: ChatRoomId, userAccountId: UserAccountId) :
        EntityIllegalStateException(
            "Chat Room Member Not Exist - ChatRoomId: ${chatRoomId.value} - UserAccountId: ${userAccountId.value}"
        )

    class ChatRoomMemberFullException(chatRoomId: ChatRoomId) :
        EntityIllegalStateException("Chat Room Member Full - ChatRoomId: ${chatRoomId.value}")
}
