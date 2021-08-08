package com.stoneconvo.unitTests.application.chatRoom

import com.stoneconvo.application.chatRoom.ChangeRoomNameApplicationService
import com.stoneconvo.application.chatRoom.command.ChangeRoomNameCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.infrastructure.persistence.chatRoom.InMemoryChatRoomRepository
import com.stoneconvo.utilities.factories.ChatRoomFactory
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
class ChangeRoomNameApplicationServiceTests(
    @Autowired
    private val changeRoomNameApplicationService: ChangeRoomNameApplicationService,
    @Autowired
    private val chatRoomRepository: InMemoryChatRoomRepository
) {
    private val defaultChatRoom = ChatRoomFactory.createRandomly()
    private val defaultNewName = ChatRoomFactory.makeChatRoomName(
        RandomStringGenerator.perform(length = 6)
    )

    @BeforeEach
    fun setUp() {
        chatRoomRepository.reset()

        chatRoomRepository.insert(defaultChatRoom)
    }

    @Test
    fun `given normal condition when room owner changes room name then succeed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.name).isNotEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = ChangeRoomNameCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            newName = defaultNewName.value,
            chatRoomId = defaultChatRoom.id.value
        )

        val roomId = changeRoomNameApplicationService.handleChangeName(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isNotEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }
    }

    @Test
    fun `given normal condition when room member changes room name then succeed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.name).isNotEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = ChangeRoomNameCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            newName = defaultNewName.value,
            chatRoomId = defaultChatRoom.id.value
        )

        val roomId = changeRoomNameApplicationService.handleChangeName(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isNotEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }
    }

    @Test
    fun `given chat room not exist when change room name then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.name).isNotEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = ChangeRoomNameCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            newName = defaultNewName.value,
            chatRoomId = Helper.generateRandomId()
        )

        assertThrows<CustomException.ChatRoomNotFoundException> {
            changeRoomNameApplicationService.handleChangeName(command)
        }

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.name).isNotEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }
    }
}
