package com.stoneconvo.unitTests.application.userAccount

import com.stoneconvo.application.command.userAccount.CreateAccountCommand
import com.stoneconvo.application.userAccount.CreateUserAccountApplicationService
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.infrastructure.persistence.administrator.InMemoryAdministratorRepository
import com.stoneconvo.infrastructure.persistence.userAccount.InMemoryUserAccountRepository
import com.stoneconvo.utilities.factories.AdministratorFactory
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
class CreateUserAccountApplicationServiceTests(
    @Autowired
    private val createUserAccountApplicationService: CreateUserAccountApplicationService,
    @Autowired
    private val userAccountRepository: InMemoryUserAccountRepository,
    @Autowired
    private val administratorRepository: InMemoryAdministratorRepository
) {
    private val defaultAdministrator = AdministratorFactory.createRandomly()
    private val defaultPassword = RandomStringGenerator.perform(length = 12)
    private val defaultPasswordHash = UserAccountFactory.makePasswordHash(defaultPassword)
    private val defaultUserName = UserAccountFactory.makeUserAccountName(
        RandomStringGenerator.perform(length = 10)
    )

    @BeforeEach
    fun setUp() {
        userAccountRepository.reset()
        administratorRepository.reset()

        administratorRepository.insert(defaultAdministrator)
    }

    @Test
    fun `given normal condition when create user account then successful`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = CreateAccountCommand.create(
            currentUserId = defaultAdministrator.id.value,
            name = defaultUserName.value,
            password = defaultPassword
        )

        val userAccountId = createUserAccountApplicationService.handleCreate(command)

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        val foundUserAccount = userAccountRepository.findByUserId(UserAccountId(userAccountId))
        assertThat(foundUserAccount?.name).isEqualTo(defaultUserName)
        assertThat(foundUserAccount?.passwordHash).isEqualTo(defaultPasswordHash)
        assertThat(foundUserAccount?.creator?.id).isEqualTo(defaultAdministrator.id)
    }

    @Test
    fun `given administrator not exist when create user account then failed`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = CreateAccountCommand.create(
            currentUserId = Helper.generateRandomId(),
            name = defaultUserName.value,
            password = defaultPassword
        )

        assertThrows<CustomException.AdministratorNotFoundException> {
            createUserAccountApplicationService.handleCreate(command)
        }

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(0)
    }

    @Test
    fun `given user name already exist when create user account then failed`() {
        /*
         * Before perform application service
         */
        val userAccount = UserAccountFactory.createRandomly()
        userAccountRepository.insert(userAccount)

        assertThat(userAccountRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = CreateAccountCommand.create(
            currentUserId = defaultAdministrator.id.value,
            name = userAccount.name.value,
            password = defaultPassword
        )

        assertThrows<CustomException.UserAccountAlreadyExistException> {
            createUserAccountApplicationService.handleCreate(command)
        }

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)
    }
}
