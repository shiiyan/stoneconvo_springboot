package com.stoneconvo.utilities.factories

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.domain.message.Message
import com.stoneconvo.domain.message.MessageContent
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.utilities.generators.RandomStringGenerator
import com.stoneconvo.utilities.generators.UserAccountIdGenerator
import java.time.LocalDateTime

object MessageFactory {
    fun makeMessageId(id: String) = MessageId(id)
    fun makeContent(content: String) = MessageContent(content)
    fun makeRoomId(roomId: String) = ChatRoomId(roomId)

    fun createRandomly(
        id: MessageId = makeMessageId(Helper.generateRandomId()),
        content: MessageContent = makeContent(RandomStringGenerator.perform(length = 200)),
        roomId: ChatRoomId = makeRoomId(Helper.generateRandomId()),
        senderId: UserAccountId = UserAccountIdGenerator.perform(),
        sentDateTime: LocalDateTime = LocalDateTime.now()
    ) = Message(
        id = id,
        content = content,
        roomId = roomId,
        senderId = senderId,
        sentDateTime = sentDateTime
    )
}
