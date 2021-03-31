package com.stoneconvo.userAccount.infrastructure.http

import com.stoneconvo.userAccount.applicationService.UserAccountApplicationService
import com.stoneconvo.userAccount.applicationService.command.CreateCommand
import com.stoneconvo.userAccount.infrastructure.http.requestBody.CreateRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user-account")
class UserAccountController(
    @Autowired
    private val userAccountApplicationService: UserAccountApplicationService
) {
    @PostMapping("/create")
    fun create(
        @Valid @RequestBody createRequestBody: CreateRequestBody,
        // TODO : create service for fetching current user id
        @CookieValue("user-id") currentUserId: String
    ): String {
        val command = CreateCommand.create(
            currentUserId = currentUserId,
            name = createRequestBody.name,
            password = createRequestBody.password
        )

        return userAccountApplicationService.create(command)
    }
}
