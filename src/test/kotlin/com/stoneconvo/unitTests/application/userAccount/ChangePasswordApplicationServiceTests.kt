package com.stoneconvo.unitTests.application.userAccount

import com.stoneconvo.application.command.userAccount.ChangePasswordCommand
import com.stoneconvo.application.userAccount.ChangePasswordApplicationService
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
class ChangePasswordApplicationServiceTests(
    @Autowired
    private val changePasswordApplicationService: ChangePasswordApplicationService,
    @Autowired
    private val userAccountRepository: InMemoryUserAccountRepository
) {
    private val defaultUserAccount = UserAccountFactory.createRandomly()
    private val defaultNewPassword = RandomStringGenerator.perform(length = 12)
    private val defaultNewPasswordHash = Helper.generateHash(defaultNewPassword)

    @BeforeEach
    fun setUp() {
        userAccountRepository.reset()

        userAccountRepository.insert(defaultUserAccount)
    }

    @Test
    fun `given normal condition when change password then succeed`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = ChangePasswordCommand.create(
            currentUserId = defaultUserAccount.id.value,
            password = defaultNewPassword
        )

        changePasswordApplicationService.handleChangePassword(command)

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        val foundUserAccountDto = userAccountRepository.findByUserId(defaultUserAccount.id)
        assertThat(foundUserAccountDto?.name).isEqualTo(defaultUserAccount.name.value)
        assertThat(foundUserAccountDto?.passwordHash).isNotEqualTo(defaultUserAccount.passwordHash.value)
        assertThat(foundUserAccountDto?.passwordHash).isEqualTo(defaultNewPasswordHash)
        assertThat(foundUserAccountDto?.creatorId).isEqualTo(defaultUserAccount.creator.id.value)
    }

    @Test
    fun `given user account not exist when change password then failed`() {
        /*
         * Before perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        /*
         * Perform application service
         */
        val command = ChangePasswordCommand.create(
            currentUserId = Helper.generateRandomId(),
            password = defaultNewPassword
        )

        assertThrows<CustomException.UserAccountNotFoundException> {
            changePasswordApplicationService.handleChangePassword(command)
        }

        /*
         * After perform application service
         */
        assertThat(userAccountRepository.count()).isEqualTo(1)

        val foundUserAccountDto = userAccountRepository.findByUserId(defaultUserAccount.id)
        assertThat(foundUserAccountDto?.name).isEqualTo(defaultUserAccount.name.value)
        assertThat(foundUserAccountDto?.passwordHash).isEqualTo(defaultUserAccount.passwordHash.value)
        assertThat(foundUserAccountDto?.passwordHash).isNotEqualTo(defaultNewPasswordHash)
        assertThat(foundUserAccountDto?.creatorId).isEqualTo(defaultUserAccount.creator.id.value)
    }
}
