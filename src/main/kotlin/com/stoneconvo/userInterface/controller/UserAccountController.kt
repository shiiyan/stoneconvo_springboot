package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.UserAccountApplicationService
import com.stoneconvo.application.command.ChangeAccountNameCommand
import com.stoneconvo.application.command.ChangePasswordCommand
import com.stoneconvo.application.command.CreateAccountCommand
import com.stoneconvo.common.authorization.AuthorizationService
import com.stoneconvo.userInterface.controller.requestBody.ChangeAccountNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.ChangePasswordRequestBody
import com.stoneconvo.userInterface.controller.requestBody.CreateAccountRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user_account")
class UserAccountController(
    @Autowired
    private val userAccountApplicationService: UserAccountApplicationService,
    @Autowired
    private val authorizationService: AuthorizationService
) {
    @PostMapping("/create")
    fun create(
        @Valid @RequestBody createAccountRequestBody: CreateAccountRequestBody
    ): String {
        val command = CreateAccountCommand.create(
            currentUserId = authorizationService.getCurrentUserId(),
            name = createAccountRequestBody.name,
            password = createAccountRequestBody.password
        )

        val accountId = userAccountApplicationService.handleCreate(command)

        return accountId
    }

    @PostMapping("/change_name")
    fun changeName(
        @Valid @RequestBody changeAccountNameRequestBody: ChangeAccountNameRequestBody
    ) {
        val command = ChangeAccountNameCommand.create(
            currentUserId = authorizationService.getCurrentUserId(),
            newName = changeAccountNameRequestBody.name
        )

        userAccountApplicationService.handleChangeName(command)
    }

    @PostMapping("/change_password")
    fun changePassword(
        @Valid @RequestBody changePasswordRequestBody: ChangePasswordRequestBody
    ) {
        val command = ChangePasswordCommand.create(
            currentUserId = authorizationService.getCurrentUserId(),
            password = changePasswordRequestBody.password
        )

        userAccountApplicationService.handleChangePassword(command)
    }
}
