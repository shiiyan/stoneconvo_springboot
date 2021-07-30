package com.stoneconvo.common.authorization

import com.stoneconvo.common.authentication.AuthUserRepository
import com.stoneconvo.common.exception.CustomException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpSession

@Component
class SessionAuthorizationService(
    @Autowired
    private val authUserRepository: AuthUserRepository,
    @Autowired
    private val httpSession: HttpSession
) : AuthorizationService {
    override fun authorize() {
        authUserRepository.findUserAccountById(this.getCurrentUserId())
            ?: throw CustomException.ForbiddenException("No Authorization")
    }

    override fun getCurrentUserId(): String = (
        httpSession.getAttribute("user-id")
            ?: throw CustomException.UserAccountNotFoundException()
        ) as String
}
