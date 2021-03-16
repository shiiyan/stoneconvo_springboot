package com.stoneconvo.repository.userAccount

import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountId
import com.stoneconvo.domain.userAccount.UserAccountName

interface UserAccountRepository {
    fun findByUserId(userId: UserAccountId): UserAccount?
    fun findByUserName(userName: UserAccountName): UserAccount?
    fun insert(userAccount: UserAccount)
    fun update(userAccount: UserAccount)
}
