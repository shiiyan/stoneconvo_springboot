package com.stoneconvo.userAccount.domain

import com.stoneconvo.administrator.domain.Administrator
import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId

class UserAccount(
    val id: UserAccountId,
    var name: UserAccountName,
    val passwordHash: PasswordHash,
    val creator: Administrator
) : Entity() {
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
}
