package com.stoneconvo.domain.userAccount

import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.administrator.Administrator

class UserAccount(
    val id: UserAccountId,
    var name: UserAccountName,
    var passwordHash: PasswordHash,
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

    fun changePassword(newPasswordHash: PasswordHash) {
        passwordHash = newPasswordHash
    }
}
