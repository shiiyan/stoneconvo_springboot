package com.stoneconvo.unitTests.application.message

import com.stoneconvo.application.message.EditMessageApplicationService
import com.stoneconvo.application.message.command.EditMessageCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.infrastructure.persistence.message.InMemoryMessageRepository
import com.stoneconvo.utilities.factories.MessageFactory
import com.stoneconvo.utilities.generators.RandomStringGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class EditMessageApplicationServiceTests(
    @Autowired
    private val editMessageApplicationService: EditMessageApplicationService,
    @Autowired
    private val messageRepository: InMemoryMessageRepository
) {
    private val defaultMessage = MessageFactory.createRandomly()
    private val defaultNewContent = RandomStringGenerator.perform(length = 200)

    @BeforeEach
    fun setUp() {
        messageRepository.reset()

        messageRepository.insert(defaultMessage)
    }

    @Test
    fun `given normal condition when edit message then succeed`() {
        /*
         * Before perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = EditMessageCommand.create(
            messageId = defaultMessage.id.value,
            currentUserId = defaultMessage.senderId.value,
            newContent = defaultNewContent
        )

        editMessageApplicationService.handleEdit(command)

        /*
         * After perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        val foundMessageDto = messageRepository.findByMessageId(defaultMessage.id)
        assertThat(foundMessageDto?.content).isEqualTo(defaultNewContent)
        assertThat(foundMessageDto?.content).isNotEqualTo(defaultMessage.content.value)
        assertThat(foundMessageDto?.roomId).isEqualTo(defaultMessage.roomId.value)
        assertThat(foundMessageDto?.senderId).isEqualTo(defaultMessage.senderId.value)
    }

    @Test
    fun `given message not exist when edit message then failed`() {
        /*
         * Before perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = EditMessageCommand.create(
            messageId = Helper.generateRandomId(),
            currentUserId = defaultMessage.senderId.value,
            newContent = defaultNewContent
        )

        assertThrows<CustomException.MessageNotFoundException> {
            editMessageApplicationService.handleEdit(command)
        }

        /*
         * After perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        val foundMessageDto = messageRepository.findByMessageId(defaultMessage.id)
        assertThat(foundMessageDto?.content).isNotEqualTo(defaultNewContent)
        assertThat(foundMessageDto?.content).isEqualTo(defaultMessage.content.value)
        assertThat(foundMessageDto?.roomId).isEqualTo(defaultMessage.roomId.value)
        assertThat(foundMessageDto?.senderId).isEqualTo(defaultMessage.senderId.value)
    }

    @Test
    fun `given message sender not exist when edit message then failed`() {
        /*
         * Before perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = EditMessageCommand.create(
            messageId = defaultMessage.id.value,
            currentUserId = Helper.generateRandomId(),
            newContent = defaultNewContent
        )

        assertThrows<CustomException.EditMessageUnauthorizedException> {
            editMessageApplicationService.handleEdit(command)
        }

        /*
         * After perform application service
         */
        assertThat(messageRepository.count()).isEqualTo(1)

        val foundMessageDto = messageRepository.findByMessageId(defaultMessage.id)
        assertThat(foundMessageDto?.content).isNotEqualTo(defaultNewContent)
        assertThat(foundMessageDto?.content).isEqualTo(defaultMessage.content.value)
        assertThat(foundMessageDto?.roomId).isEqualTo(defaultMessage.roomId.value)
        assertThat(foundMessageDto?.senderId).isEqualTo(defaultMessage.senderId.value)
    }
}
