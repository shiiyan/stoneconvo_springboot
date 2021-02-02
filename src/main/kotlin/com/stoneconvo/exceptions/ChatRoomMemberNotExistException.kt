package com.stoneconvo.exceptions

import com.stoneconvo.domain.chatroom.ChatRoomId
import com.stoneconvo.domain.userAccount.UserAccountId

class ChatRoomMemberNotExistException(chatRoomId: ChatRoomId, userAccountId: UserAccountId) :
    Exception("Chat Room Member Not Exist - ChatRoomId: $chatRoomId - UserAccountId: $userAccountId")
