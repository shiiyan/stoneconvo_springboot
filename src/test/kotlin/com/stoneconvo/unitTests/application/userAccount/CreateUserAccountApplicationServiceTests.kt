package com.stoneconvo.unitTests.application.userAccount

import com.stoneconvo.application.command.userAccount.CreateAccountCommand
import com.stoneconvo.application.userAccount.CreateUserAccountApplicationService
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.infrastructure.persistence.administrator.InMemoryAdministratorRepository
import com.stoneconvo.infrastructure.persistence.userAccount.InMemoryUserAccountRepository
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
    @BeforeEach
    fun setUp() {
        userAccountRepository.reset()
        administratorRepository.reset()
    }

    @Test
    fun `create user account failed when administrator not exist`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(0)
        assertThat(administratorRepository.count()).isEqualTo(0)

        /*
         * Perform application service
         */
        val command = CreateAccountCommand.create(
            currentUserId = Helper.generateRandomId(),
            name = "test-user",
            password = "test"
        )

        assertThrows<CustomException.AdministratorNotFoundException> {
            createUserAccountApplicationService.handleCreate(command)
        }

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(0)
        assertThat(administratorRepository.count()).isEqualTo(0)
    }
}
