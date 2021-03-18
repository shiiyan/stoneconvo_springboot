package com.stoneconvo.domain.userAccount

import com.stoneconvo.exceptions.UserAccountNotFoundException
import com.stoneconvo.repository.userAccount.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserAccountService(
    @Autowired
    private val userAccountRepository: UserAccountRepository,
) {
    // check duplicate name

    // generate password hash ??

    // login
    fun login(
        name: String,
        password: String
    ): UserAccount {
        val foundUserAccount = userAccountRepository.findByUserName(UserAccountName(name))
            ?: throw UserAccountNotFoundException(
                userName = name
            )

        val passwordHash = PasswordHash.create(password)

        if (!foundUserAccount.verify(passwordHash)) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }

        return foundUserAccount
    }

    // register
}
