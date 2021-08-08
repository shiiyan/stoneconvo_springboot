package com.stoneconvo.unitTests.application.chatRoom

import com.stoneconvo.application.chatRoom.ChangeMemberNameApplicationService
import com.stoneconvo.application.chatRoom.command.ChangeMemberNameCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.chatRoom.roomMember.RoomMemberName
import com.stoneconvo.infrastructure.persistence.chatRoom.InMemoryChatRoomRepository
import com.stoneconvo.utilities.factories.ChatRoomFactory
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
class ChangeMemberNameApplicationServiceTests(
    @Autowired
    private val changeMemberNameApplicationService: ChangeMemberNameApplicationService,
    @Autowired
    private val chatRoomRepository: InMemoryChatRoomRepository
) {
    private val defaultChatRoom = ChatRoomFactory.createRandomly(
        members = (1..2).map {
            ChatRoomFactory.makeRoomMember(
                name = RandomStringGenerator.perform(length = 10),
                userAccountId = UserAccountIdGenerator.perform()
            )
        }.toMutableList()
    )
    private val defaultNewName = RoomMemberName(RandomStringGenerator.perform(length = 10))

    @BeforeEach
    fun setUp() {
        chatRoomRepository.reset()

        chatRoomRepository.insert(defaultChatRoom)
    }

    @Test
    fun `given normal condition when room owner changes member name then succeed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }

        /*
         * Perform application service
         */
        val command = ChangeMemberNameCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            userAccountId = defaultChatRoom.members.first().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value,
            name = defaultNewName.value
        )

        changeMemberNameApplicationService.handleChangeMemberName(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isNotEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }
    }

    @Test
    fun `given normal condition when room member changes its member name then succeed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }

        /*
         * Perform application service
         */
        val command = ChangeMemberNameCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            userAccountId = defaultChatRoom.members.first().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value,
            name = defaultNewName.value
        )

        changeMemberNameApplicationService.handleChangeMemberName(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isNotEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultNewName.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }
    }

    @Test
    fun `given normal condition when room member changes other member's name then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }

        /*
         * Perform application service
         */
        val command = ChangeMemberNameCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            userAccountId = defaultChatRoom.members.last().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value,
            name = defaultNewName.value
        )

        assertThrows<CustomException.ChangeMemberNameUnauthorizedException> {
            changeMemberNameApplicationService.handleChangeMemberName(command)
        }

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }
    }

    @Test
    fun `given not belong to room when room member changes its name then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }

        /*
         * Perform application service
         */
        val command = ChangeMemberNameCommand.create(
            currentUserId = Helper.generateRandomId(),
            userAccountId = defaultChatRoom.members.last().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value,
            name = defaultNewName.value
        )

        assertThrows<CustomException.ChangeMemberNameUnauthorizedException> {
            changeMemberNameApplicationService.handleChangeMemberName(command)
        }

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }
    }

    @Test
    fun `given chat room not exist when room owner changes member's name then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }

        /*
         * Perform application service
         */
        val command = ChangeMemberNameCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            userAccountId = defaultChatRoom.members.last().userAccountId.value,
            chatRoomId = Helper.generateRandomId(),
            name = defaultNewName.value
        )

        assertThrows<CustomException.ChatRoomNotFoundException> {
            changeMemberNameApplicationService.handleChangeMemberName(command)
        }

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }
    }

    @Test
    fun `given room member not exist when room owner changes member's name then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }

        /*
         * Perform application service
         */
        val command = ChangeMemberNameCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            userAccountId = Helper.generateRandomId(),
            chatRoomId = defaultChatRoom.id.value,
            name = defaultNewName.value
        )

        assertThrows<CustomException.ChatRoomMemberNotExistException> {
            changeMemberNameApplicationService.handleChangeMemberName(command)
        }

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.first()?.name)
                .isEqualTo(defaultChatRoom.members.first().name.value)
            assertThat(foundChatRoomDto?.members?.last()?.name)
                .isEqualTo(defaultChatRoom.members.last().name.value)
        }
    }
}
