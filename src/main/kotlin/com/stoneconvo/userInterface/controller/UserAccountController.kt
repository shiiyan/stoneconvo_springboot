package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.command.userAccount.ChangeAccountNameCommand
import com.stoneconvo.application.command.userAccount.ChangePasswordCommand
import com.stoneconvo.application.command.userAccount.CreateAccountCommand
import com.stoneconvo.application.userAccount.ChangeNameApplicationService
import com.stoneconvo.application.userAccount.ChangePasswordApplicationService
import com.stoneconvo.application.userAccount.CreateUserAccountApplicationService
import com.stoneconvo.common.authorization.AuthorizationService
import com.stoneconvo.userInterface.controller.requestBody.userAccount.ChangeAccountNameRequestBody
import com.stoneconvo.userInterface.controller.requestBody.userAccount.ChangePasswordRequestBody
import com.stoneconvo.userInterface.controller.requestBody.userAccount.CreateAccountRequestBody
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
    private val createUserAccountApplicationService: CreateUserAccountApplicationService,
    @Autowired
    private val changeNameApplicationService: ChangeNameApplicationService,
    @Autowired
    private val changePasswordApplicationService: ChangePasswordApplicationService,
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

        val accountId = createUserAccountApplicationService.handleCreate(command)

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

        changeNameApplicationService.handleChangeName(command)
    }

    @PostMapping("/change_password")
    fun changePassword(
        @Valid @RequestBody changePasswordRequestBody: ChangePasswordRequestBody
    ) {
        val command = ChangePasswordCommand.create(
            currentUserId = authorizationService.getCurrentUserId(),
            password = changePasswordRequestBody.password
        )

        changePasswordApplicationService.handleChangePassword(command)
    }
}
