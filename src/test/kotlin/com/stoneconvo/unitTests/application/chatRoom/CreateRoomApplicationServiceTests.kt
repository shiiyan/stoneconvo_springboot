package com.stoneconvo.unitTests.application.chatRoom

import com.stoneconvo.application.chatRoom.CreateRoomApplicationService
import com.stoneconvo.application.chatRoom.command.CreateRoomCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.chatRoom.ChatRoomId
import com.stoneconvo.infrastructure.persistence.administrator.InMemoryAdministratorRepository
import com.stoneconvo.infrastructure.persistence.chatRoom.InMemoryChatRoomRepository
import com.stoneconvo.utilities.factories.AdministratorFactory
import com.stoneconvo.utilities.factories.ChatRoomFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CreateRoomApplicationServiceTests(
    @Autowired
    private val createRoomApplicationService: CreateRoomApplicationService,
    @Autowired
    private val administratorRepository: InMemoryAdministratorRepository,
    @Autowired
    private val chatRoomRepository: InMemoryChatRoomRepository
) {
    private val defaultAdministrator = AdministratorFactory.createRandomly()
    private val defaultChatRoom = ChatRoomFactory.createRandomly(
        owner = defaultAdministrator
    )

    @BeforeEach
    fun setUp() {
        administratorRepository.reset()
        chatRoomRepository.reset()

        administratorRepository.insert(defaultAdministrator)
    }

    @Test
    fun `given normal condition when create room then succeed`() {
        /*
         * Before perform application service
         */
        assertThat(chatRoomRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = CreateRoomCommand.create(
            currentUserId = defaultAdministrator.id.value,
            name = defaultChatRoom.name.value
        )

        val roomId = createRoomApplicationService.handleCreate(command)

        /*
         * After perform application service
         */
        assertThat(chatRoomRepository.count()).isEqualTo(1)

        val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = ChatRoomId(roomId))
        assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
        assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultAdministrator.id.value)
        assertThat(foundChatRoomDto?.members?.size).isEqualTo(0)
    }

    @Test
    fun `given administrator not exist when create room then failed`() {
        /*
         * Before perform application service
         */
        assertThat(chatRoomRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = CreateRoomCommand.create(
            currentUserId = Helper.generateRandomId(),
            name = defaultChatRoom.name.value
        )

        assertThrows<CustomException.AdministratorNotFoundException> {
            createRoomApplicationService.handleCreate(command)
        }

        /*
         * After perform application service
         */
        assertThat(chatRoomRepository.count()).isEqualTo(0)
    }
}
