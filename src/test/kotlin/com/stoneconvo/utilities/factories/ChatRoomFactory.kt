package com.stoneconvo.utilities.factories

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.chatRoom.ChatRoom
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.chatRoom.ChatRoomName
import com.stoneconvo.domain.chatRoom.roomMember.RoomMember
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName
import com.stoneconvo.utilities.generators.RandomStringGenerator
import com.stoneconvo.utilities.generators.UserAccountIdGenerator

object ChatRoomFactory {
    fun makeChatRoomId(id: String) = ChatRoomId(id)
    fun makeChatRoomName(name: String) = ChatRoomName(name)
    fun makeOwner(userAccountId: UserAccountId) = Administrator(userAccountId)
    fun makeRoomMember(name: String, userAccountId: UserAccountId) = RoomMember(
        name = RoomMemberName(name),
        userAccountId = userAccountId
    )

    fun createRandomly(
        id: ChatRoomId = makeChatRoomId(Helper.generateRandomId()),
        name: ChatRoomName = makeChatRoomName(RandomStringGenerator.perform(length = 6)),
        owner: Administrator = makeOwner(UserAccountIdGenerator.perform()),
        members: MutableList<RoomMember> = mutableListOf(
            makeRoomMember(
                name = RandomStringGenerator.perform(length = 6),
                userAccountId = UserAccountIdGenerator.perform()
            )
        )
    ) = ChatRoom(
        id = id,
        name = name,
        owner = owner,
        members = members
    )
}
