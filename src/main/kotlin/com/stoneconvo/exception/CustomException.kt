package com.stoneconvo.exception

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.domain.userAccount.UserAccountId
import java.lang.IllegalStateException

class CustomException {
    open class EntityNotFoundException(errorMessage: String) : IllegalStateException(errorMessage)

    class AdministratorNotFoundException(userId: UserAccountId) :
        EntityNotFoundException("Administrator Not Found - UserId: $userId")

    class ChatRoomNotFoundException(chatRoomId: ChatRoomId) :
        EntityNotFoundException("Chat Room Not Found - ChatRoomId: $chatRoomId")

    class MessageNotFoundException(messageId: MessageId) :
        EntityNotFoundException("Message Not Found - MessageId: $messageId")

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
            "Chat Room Member Exists - ChatRoomId: $chatRoomId - UserAccountId: $userAccountId"
        )

    class ChatRoomMemberNotExistException(chatRoomId: ChatRoomId, userAccountId: UserAccountId) :
        EntityIllegalStateException(
            "Chat Room Member Not Exist - ChatRoomId: $chatRoomId - UserAccountId: $userAccountId"
        )

    class ChatRoomMemberFullException(chatRoomId: ChatRoomId) :
        EntityIllegalStateException("Chat Room Member Full - ChatRoomId: $chatRoomId")
}
