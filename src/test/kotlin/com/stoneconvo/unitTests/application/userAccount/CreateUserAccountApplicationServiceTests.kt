package com.stoneconvo.unitTests.application.userAccount

import com.stoneconvo.application.command.userAccount.CreateAccountCommand
import com.stoneconvo.application.userAccount.CreateUserAccountApplicationService
import com.stoneconvo.common.exception.CustomException
import com.stoneconvo.common.helper.Helper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CreateUserAccountApplicationServiceTests(
    @Autowired
    private val createUserAccountApplicationService: CreateUserAccountApplicationService
) {
    @Test
    fun `create user account failed when administrator not exist`() {
        // TODO: change to user in memory database
        val command = CreateAccountCommand.create(
            currentUserId = Helper.generateRandomId(),
            name = "test-user",
            password = "test"
        )

        assertThrows<CustomException.AdministratorNotFoundException> {
            createUserAccountApplicationService.handleCreate(command)
        }
    }
}
