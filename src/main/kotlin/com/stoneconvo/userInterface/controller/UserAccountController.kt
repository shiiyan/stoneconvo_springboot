package com.stoneconvo.userInterface.controller

import com.stoneconvo.application.UserAccountApplicationService
import com.stoneconvo.application.command.CreateAccountCommand
import com.stoneconvo.userInterface.controller.requestBody.CreateAccountRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user_account")
class UserAccountController(
    @Autowired
    private val userAccountApplicationService: UserAccountApplicationService
) {
    @PostMapping("/create")
    fun create(
        @Valid @RequestBody createAccountRequestBody: CreateAccountRequestBody,
        // TODO : create service for fetching current user id
        @CookieValue("user-id") currentUserId: String
    ): String {
        val command = CreateAccountCommand.create(
            currentUserId = currentUserId,
            name = createAccountRequestBody.name,
            password = createAccountRequestBody.password
        )

        return userAccountApplicationService.create(command)
    }
}
