package com.stoneconvo.common.authentication

import com.stoneconvo.common.authentication.RequestBody.LoginRequestBody
import com.stoneconvo.common.exception.CustomException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RestController
class AuthenticationController(
    @Autowired
    private val authUserRepository: AuthUserRepository,
    @Autowired
    private val httpSession: HttpSession
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestBody, response: HttpServletResponse): String {
        val foundUserAccount = authUserRepository.findUserAccountByName(request.name)
            ?: throw CustomException.UserAccountNotFoundException(
                userName = request.name
            )
        if (
            !foundUserAccount.verify(userAccountName = request.name, password = request.password)
        ) {
            throw CustomException.UnauthorizedException("Username Or Password Not Valid")
        }

        httpSession.setAttribute("user-id", foundUserAccount.userAccountId)

        return foundUserAccount.userAccountId
    }

    @PostMapping("/logout")
    fun logout() {
        httpSession.removeAttribute("user-id")
    }
}
