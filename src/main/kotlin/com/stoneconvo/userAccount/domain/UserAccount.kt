package com.stoneconvo.userAccount.domain

import com.stoneconvo.administrator.domain.Administrator

class UserAccount(
    val id: UserAccountId,
    var name: UserAccountName,
    val passwordHash: PasswordHash,
    val creator: Administrator
) {
    companion object {
        fun create(
            name: UserAccountName,
            passwordHash: PasswordHash,
            creator: Administrator,
        ) = UserAccount(
            id = UserAccountId.create(),
            name = name,
            passwordHash = passwordHash,
            creator = creator
        )
    }

    fun changeName(newName: UserAccountName) {
        name = newName
    }

    fun verify(passwordHashToVerify: PasswordHash) = passwordHash.isSameWith(passwordHashToVerify)
}
