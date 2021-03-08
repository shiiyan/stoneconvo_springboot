package com.stoneconvo.repository.userAccount

import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountId

interface UserAccountRepository {
    fun findByUserId(userId: UserAccountId): UserAccount?
    fun insert(userAccount: UserAccount)
    fun update(userAccount: UserAccount)
}
