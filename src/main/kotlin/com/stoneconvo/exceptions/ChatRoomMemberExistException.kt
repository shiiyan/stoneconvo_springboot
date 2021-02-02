package com.stoneconvo.exceptions

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.userAccount.UserAccountId

class ChatRoomMemberExistException(chatRoomId: ChatRoomId, userAccountId: UserAccountId) :
    Exception("Chat Room Member Exists - ChatRoomId: $chatRoomId - UserAccountId: $userAccountId")
