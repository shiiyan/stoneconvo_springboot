package com.stoneconvo.userAccount.domain

import com.stoneconvo.common.domain.UserAccountId

interface UserAccountRepository {
    fun findByUserId(userId: UserAccountId): UserAccount?
    fun findByUserName(userName: UserAccountName): UserAccount?
    fun insert(userAccount: UserAccount)
    fun update(userAccount: UserAccount)
}
