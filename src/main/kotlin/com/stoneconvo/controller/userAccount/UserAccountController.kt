package com.stoneconvo.controller.userAccount

import com.stoneconvo.applicationService.userAccount.UserAccountApplicationService
import com.stoneconvo.applicationService.userAccount.command.LoginCommand
import com.stoneconvo.controller.userAccount.RequestBody.LoginRequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/user_account")
class UserAccountController(
    @Autowired
    private val userAccountApplicationService: UserAccountApplicationService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestBody, response: HttpServletResponse): String {
        val currentUserId = userAccountApplicationService.login(
            LoginCommand(
                name = request.name,
                password = request.password
            )
        ).id.value

        response.addCookie(
            Cookie("user-id", currentUserId)
        )

        return currentUserId
    }

    @GetMapping("/logout")
    fun logout() {
    }
}
