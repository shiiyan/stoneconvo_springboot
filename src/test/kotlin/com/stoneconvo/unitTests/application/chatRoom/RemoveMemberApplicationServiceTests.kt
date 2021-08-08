package com.stoneconvo.unitTests.application.chatRoom

import com.stoneconvo.application.chatRoom.RemoveMemberApplicationService
import com.stoneconvo.application.chatRoom.command.RemoveMemberCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
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
class RemoveMemberApplicationServiceTests(
    @Autowired
    private val removeMemberApplicationService: RemoveMemberApplicationService,
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

    @BeforeEach
    fun setUp() {
        chatRoomRepository.reset()

        chatRoomRepository.insert(defaultChatRoom)
    }

    @Test
    fun `given normal condition when room owner removes member then succeed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = RemoveMemberCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            userAccountId = defaultChatRoom.members.first().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        removeMemberApplicationService.handleRemoveMember(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isNotEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size - 1)
            assertThat(foundChatRoomDto?.members?.first()?.userAccountId)
                .isNotEqualTo(defaultChatRoom.members.first().userAccountId.value)
            assertThat(foundChatRoomDto?.members?.first()?.userAccountId)
                .isEqualTo(defaultChatRoom.members.last().userAccountId.value)
        }
    }

    @Test
    fun `given normal condition when room member removes itself then succeed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = RemoveMemberCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            userAccountId = defaultChatRoom.members.first().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        removeMemberApplicationService.handleRemoveMember(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isNotEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size - 1)
            assertThat(foundChatRoomDto?.members?.first()?.userAccountId)
                .isNotEqualTo(defaultChatRoom.members.first().userAccountId.value)
            assertThat(foundChatRoomDto?.members?.first()?.userAccountId)
                .isEqualTo(defaultChatRoom.members.last().userAccountId.value)
        }
    }

    @Test
    fun `given normal condition when room member removes other members then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = RemoveMemberCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            userAccountId = defaultChatRoom.members.last().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.RemoveMemberUnauthorizedException> {
            removeMemberApplicationService.handleRemoveMember(command)
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
        }
    }

    @Test
    fun `given not belong to room when room member removes member then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = RemoveMemberCommand.create(
            currentUserId = Helper.generateRandomId(),
            userAccountId = defaultChatRoom.members.first().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.RemoveMemberUnauthorizedException> {
            removeMemberApplicationService.handleRemoveMember(command)
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
        }
    }

    @Test
    fun `given chat room not exist when room member removes itself then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = RemoveMemberCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            userAccountId = defaultChatRoom.members.last().userAccountId.value,
            chatRoomId = Helper.generateRandomId()
        )

        assertThrows<CustomException.ChatRoomNotFoundException> {
            removeMemberApplicationService.handleRemoveMember(command)
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
        }
    }

    @Test
    fun `given member not exist when room owner removes member then failed`() {
        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = RemoveMemberCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            userAccountId = Helper.generateRandomId(),
            chatRoomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.ChatRoomMemberNotExistException> {
            removeMemberApplicationService.handleRemoveMember(command)
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
        }
    }
}
