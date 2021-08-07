package com.stoneconvo.unitTests.application.userAccount

import com.stoneconvo.application.userAccount.ChangeNameApplicationService
import com.stoneconvo.application.userAccount.command.ChangeAccountNameCommand
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.infrastructure.persistence.userAccount.InMemoryUserAccountRepository
import com.stoneconvo.utilities.factories.UserAccountFactory
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
class ChangeNameApplicationServiceTests(
    @Autowired
    private val changeNameApplicationService: ChangeNameApplicationService,
    @Autowired
    private val userAccountRepository: InMemoryUserAccountRepository
) {
    private val defaultUserAccount = UserAccountFactory.createRandomly()
    private val defaultNewName = UserAccountFactory.makeUserAccountName(
        RandomStringGenerator.perform(length = 10)
    )

    @BeforeEach
    fun setUp() {
        userAccountRepository.reset()

        userAccountRepository.insert(defaultUserAccount)
    }

    @Test
    fun `give normal condition when change name then succeed`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = ChangeAccountNameCommand.create(
            currentUserId = defaultUserAccount.id.value,
            newName = defaultNewName.value
        )

        changeNameApplicationService.handleChangeName(command)

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        val foundUserAccountDto = userAccountRepository.findByUserId(defaultUserAccount.id)
        assertThat(foundUserAccountDto?.name).isEqualTo(defaultNewName.value)
        assertThat(foundUserAccountDto?.name).isNotEqualTo(defaultUserAccount.name.value)
        assertThat(foundUserAccountDto?.passwordHash).isEqualTo(defaultUserAccount.passwordHash.value)
        assertThat(foundUserAccountDto?.creatorId).isEqualTo(defaultUserAccount.creator.id.value)
    }

    @Test
    fun `given user account not exist when change name then failed`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = ChangeAccountNameCommand.create(
            currentUserId = Helper.generateRandomId(),
            newName = defaultNewName.value
        )

        assertThrows<CustomException.UserAccountNotFoundException> {
            changeNameApplicationService.handleChangeName(command)
        }

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        val foundUserAccountDto = userAccountRepository.findByUserId(defaultUserAccount.id)
        assertThat(foundUserAccountDto?.name).isNotEqualTo(defaultNewName.value)
        assertThat(foundUserAccountDto?.name).isEqualTo(defaultUserAccount.name.value)
        assertThat(foundUserAccountDto?.passwordHash).isEqualTo(defaultUserAccount.passwordHash.value)
        assertThat(foundUserAccountDto?.creatorId).isEqualTo(defaultUserAccount.creator.id.value)
    }

    @Test
    fun `given user name exist when change name then failed`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = ChangeAccountNameCommand.create(
            currentUserId = defaultUserAccount.id.value,
            newName = defaultUserAccount.name.value
        )

        assertThrows<CustomException.UserAccountAlreadyExistException> {
            changeNameApplicationService.handleChangeName(command)
        }

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        val foundUserAccountDto = userAccountRepository.findByUserId(defaultUserAccount.id)
        assertThat(foundUserAccountDto?.name).isNotEqualTo(defaultNewName.value)
        assertThat(foundUserAccountDto?.name).isEqualTo(defaultUserAccount.name.value)
        assertThat(foundUserAccountDto?.passwordHash).isEqualTo(defaultUserAccount.passwordHash.value)
        assertThat(foundUserAccountDto?.creatorId).isEqualTo(defaultUserAccount.creator.id.value)
    }
}
