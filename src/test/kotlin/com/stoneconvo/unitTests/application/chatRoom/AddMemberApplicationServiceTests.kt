package com.stoneconvo.unitTests.application.chatRoom

import com.stoneconvo.application.chatRoom.AddMemberApplicationService
import com.stoneconvo.application.chatRoom.command.AddMemberCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.infrastructure.persistence.chatRoom.InMemoryChatRoomRepository
import com.stoneconvo.infrastructure.persistence.userAccount.InMemoryUserAccountRepository
import com.stoneconvo.utilities.factories.ChatRoomFactory
import com.stoneconvo.utilities.factories.UserAccountFactory
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
class AddMemberApplicationServiceTests(
    @Autowired
    private val addMemberApplicationService: AddMemberApplicationService,
    @Autowired
    private val chatRoomRepository: InMemoryChatRoomRepository,
    @Autowired
    private val userAccountRepository: InMemoryUserAccountRepository
) {
    private val defaultChatRoom = ChatRoomFactory.createRandomly()
    private val defaultUserAccount = UserAccountFactory.createRandomly()
    private val defaultNewMember = ChatRoomFactory.makeRoomMember(
        name = RandomStringGenerator.perform(length = 10),
        userAccountId = defaultUserAccount.id
    )

    @BeforeEach
    fun setUp() {
        chatRoomRepository.reset()

        chatRoomRepository.insert(defaultChatRoom)
        userAccountRepository.insert(defaultUserAccount)
    }

    @Test
    fun `given normal condition when room owner adds member then succeed`() {
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
        val command = AddMemberCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            name = defaultNewMember.name.value,
            userAccountId = defaultNewMember.userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        addMemberApplicationService.handleAddMember(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isNotEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size + 1)
            assertThat(foundChatRoomDto?.members?.last()?.name).isEqualTo(defaultNewMember.name.value)
            assertThat(foundChatRoomDto?.members?.last()?.userAccountId)
                .isEqualTo(defaultNewMember.userAccountId.value)
        }
    }

    @Test
    fun `given normal condition when room member adds member then succeed`() {
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
        val command = AddMemberCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            name = defaultNewMember.name.value,
            userAccountId = defaultNewMember.userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        addMemberApplicationService.handleAddMember(command)

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(1)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isNotEqualTo(defaultChatRoom.members.size)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size + 1)
            assertThat(foundChatRoomDto?.members?.last()?.name).isEqualTo(defaultNewMember.name.value)
            assertThat(foundChatRoomDto?.members?.last()?.userAccountId)
                .isEqualTo(defaultNewMember.userAccountId.value)
        }
    }

    @Test
    fun `given chat room not exist when room member adds member then failed`() {
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
        val command = AddMemberCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            name = defaultNewMember.name.value,
            userAccountId = defaultNewMember.userAccountId.value,
            chatRoomId = Helper.generateRandomId()
        )

        assertThrows<CustomException.ChatRoomNotFoundException> {
            addMemberApplicationService.handleAddMember(command)
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
    fun `given user account not exist when room member adds member then failed`() {
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
        val command = AddMemberCommand.create(
            currentUserId = defaultChatRoom.members.first().userAccountId.value,
            name = defaultNewMember.name.value,
            userAccountId = Helper.generateRandomId(),
            chatRoomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.UserAccountNotFoundException> {
            addMemberApplicationService.handleAddMember(command)
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
    fun `given not belong to room when adds member then failed`() {
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
        val command = AddMemberCommand.create(
            currentUserId = Helper.generateRandomId(),
            name = defaultNewMember.name.value,
            userAccountId = defaultNewMember.userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.AddRoomMemberUnauthorizedException> {
            addMemberApplicationService.handleAddMember(command)
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
    fun `given room member full when adds member then failed`() {
        val defaultFullChatRoom = ChatRoomFactory.createRandomly(
            members = (1..30).map {
                ChatRoomFactory.makeRoomMember(
                    name = RandomStringGenerator.perform(length = 10),
                    userAccountId = UserAccountIdGenerator.perform()
                )
            }.toMutableList()
        )
        chatRoomRepository.insert(defaultFullChatRoom)

        /*
         * Before perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(2)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)

            val foundFullChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultFullChatRoom.id)
            assertThat(foundFullChatRoomDto?.name).isEqualTo(defaultFullChatRoom.name.value)
            assertThat(foundFullChatRoomDto?.ownerId).isEqualTo(defaultFullChatRoom.owner.id.value)
            assertThat(foundFullChatRoomDto?.members?.size).isEqualTo(defaultFullChatRoom.members.size)
        }

        /*
         * Perform application service
         */
        val command = AddMemberCommand.create(
            currentUserId = defaultFullChatRoom.members.first().userAccountId.value,
            name = defaultNewMember.name.value,
            userAccountId = defaultNewMember.userAccountId.value,
            chatRoomId = defaultFullChatRoom.id.value
        )

        assertThrows<CustomException.ChatRoomMemberFullException> {
            addMemberApplicationService.handleAddMember(command)
        }

        /*
         * After perform application service
         */
        run {
            assertThat(chatRoomRepository.count()).isEqualTo(2)

            val foundChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultChatRoom.id)
            assertThat(foundChatRoomDto?.name).isEqualTo(defaultChatRoom.name.value)
            assertThat(foundChatRoomDto?.ownerId).isEqualTo(defaultChatRoom.owner.id.value)
            assertThat(foundChatRoomDto?.members?.size).isEqualTo(defaultChatRoom.members.size)

            val foundFullChatRoomDto = chatRoomRepository.findByRoomId(roomId = defaultFullChatRoom.id)
            assertThat(foundFullChatRoomDto?.name).isEqualTo(defaultFullChatRoom.name.value)
            assertThat(foundFullChatRoomDto?.ownerId).isEqualTo(defaultFullChatRoom.owner.id.value)
            assertThat(foundFullChatRoomDto?.members?.size).isEqualTo(defaultFullChatRoom.members.size)
        }
    }

    @Test
    fun `given member already exist when adds member then failed`() {
        val defaultUserAccountAsRoomMember = UserAccountFactory.createRandomly(
            id = defaultChatRoom.members.first().userAccountId
        )

        userAccountRepository.insert(defaultUserAccountAsRoomMember)

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
        val command = AddMemberCommand.create(
            currentUserId = defaultChatRoom.owner.id.value,
            name = defaultNewMember.name.value,
            userAccountId = defaultChatRoom.members.first().userAccountId.value,
            chatRoomId = defaultChatRoom.id.value
        )

        assertThrows<CustomException.ChatRoomMemberExistException> {
            addMemberApplicationService.handleAddMember(command)
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
