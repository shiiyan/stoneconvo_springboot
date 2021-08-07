package com.stoneconvo.unitTests.application.message

import com.stoneconvo.application.message.SendMessageApplicationService
import com.stoneconvo.application.message.command.SendMessageCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.message.MessageId
import com.stoneconvo.infrastructure.persistence.chatRoom.InMemoryChatRoomRepository
import com.stoneconvo.infrastructure.persistence.message.InMemoryMessageRepository
import com.stoneconvo.utilities.factories.ChatRoomFactory
import com.stoneconvo.utilities.factories.MessageFactory
import com.stoneconvo.utilities.generators.RandomStringGenerator
import com.stoneconvo.utilities.generators.UserAccountIdGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class SendMessageApplicationServiceTests(
    @Autowired
    private val sendMessageApplicationService: SendMessageApplicationService,
    @Autowired
    private val chatRoomRepository: InMemoryChatRoomRepository,
    @Autowired
    private val messageRepository: InMemoryMessageRepository
) {
    private val defaultUserAccountId = UserAccountIdGenerator.perform()
    private val defaultChatRoom = ChatRoomFactory.createRandomly(
        members = mutableListOf(
            ChatRoomFactory.makeRoomMember(
                name = RandomStringGenerator.perform(length = 5),
                userAccountId = defaultUserAccountId
            )
        )
    )
    private val defaultMessage = MessageFactory.createRandomly(
        roomId = defaultChatRoom.id,
        senderId = defaultUserAccountId
    )

    @BeforeEach
    fun setUp() {
        chatRoomRepository.reset()
        messageRepository.reset()

        chatRoomRepository.insert(defaultChatRoom)
    }

    @Test
    fun `given normal condition when send message then succeed`() {
        /*
         * Before perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = SendMessageCommand.create(
            currentUserId = defaultUserAccountId.value,
            messageContent = defaultMessage.content.value,
            roomId = defaultChatRoom.id.value
        )

        val messageId = sendMessageApplicationService.handleSend(command)

        /*
         * After perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        val foundMessageDto = messageRepository.findByMessageId(MessageId(messageId))
        assertThat(foundMessageDto?.content).isEqualTo(defaultMessage.content.value)
        assertThat(foundMessageDto?.roomId).isEqualTo(defaultChatRoom.id.value)
        assertThat(foundMessageDto?.senderId).isEqualTo(defaultUserAccountId.value)
    }

    @Test
    fun `given chat room not exist when send message then failed`() {
        /*
           * Before perform application service
           */
        assertThat(messageRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = SendMessageCommand.create(
            currentUserId = defaultUserAccountId.value,
            messageContent = defaultMessage.content.value,
            roomId = Helper.generateRandomId()
        )

        assertThrows<CustomException.ChatRoomNotFoundException> {
            sendMessageApplicationService.handleSend(command)
        }

        /*
         * After perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(0)
    }

    @Test
    fun `given room membernot exist when send message then failed`() {
        /*
           * Before perform application service
           */
        assertThat(messageRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = SendMessageCommand.create(
            currentUserId = Helper.generateRandomId(),
            messageContent = defaultMessage.content.value,
            roomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.ChatRoomMemberNotExistException> {
            sendMessageApplicationService.handleSend(command)
        }

        /*
         * After perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(0)
    }
}
