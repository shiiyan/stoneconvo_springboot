package com.stoneconvo.userAccount.domain

interface UserAccountRepository {
    fun findByUserId(userId: UserAccountId): UserAccount?
    fun findByUserName(userName: UserAccountName): UserAccount?
    fun insert(userAccount: UserAccount)
    fun update(userAccount: UserAccount)
}
