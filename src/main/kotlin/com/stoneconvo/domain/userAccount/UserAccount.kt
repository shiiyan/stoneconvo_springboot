package com.stoneconvo.domain.userAccount

import com.stoneconvo.domain.administrator.Administrator

class UserAccount(
    val id: UserAccountId,
    var name: UserAccountName,
    private val passwordHash: PasswordHash,
    val creator: Administrator
) {
    fun changeName(newName: UserAccountName) {
        name = newName
    }

    fun verify(passwordHashToVerify: PasswordHash) = passwordHash.isSameWith(passwordHashToVerify)
}
