package com.stoneconvo.common.authentication.persistence

import com.stoneconvo.common.authentication.AuthUser
import com.stoneconvo.common.authentication.AuthUserRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("test")
class InMemoryAuthUserRepository : AuthUserRepository {
    private val authUsers: MutableList<AuthUser> = mutableListOf()

    override fun findUserAccountByName(name: String): AuthUser? =
        authUsers.find { it.accountName == name }

    override fun findUserAccountById(id: String): AuthUser? =
        authUsers.find { it.userAccountId == id }
}
