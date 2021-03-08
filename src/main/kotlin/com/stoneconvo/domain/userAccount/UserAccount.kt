package com.stoneconvo.domain.userAccount

import com.stoneconvo.domain.administrator.Administrator

class UserAccount(
    val id: UserAccountId,
    var name: UserAccountName,
    private val passwordHash: PasswordHash,
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
