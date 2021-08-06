package com.stoneconvo.domain.userAccount

import com.stoneconvo.common.domain.UserAccountId

interface UserAccountRepository {
    fun findByUserId(userId: UserAccountId): UserAccount.Dto?
    fun findByUserName(userName: UserAccountName): UserAccount.Dto?
    fun insert(userAccount: UserAccount)
    fun update(userAccount: UserAccount)
}
