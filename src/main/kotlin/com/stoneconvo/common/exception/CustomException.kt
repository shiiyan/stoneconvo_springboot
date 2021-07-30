package com.stoneconvo.common.exception

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.message.MessageId
import java.lang.IllegalStateException

class CustomException {
    class UnauthorizedException(errorMessage: String) : IllegalStateException(errorMessage)

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

    open class EntityAlreadyExistException(errorMessage: String) : IllegalStateException(errorMessage)

    class UserAccountAlreadyExistException(
        userId: String? = null,
        userName: String? = null
    ) : EntityAlreadyExistException(
        "User Account Already Exist - ${
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
