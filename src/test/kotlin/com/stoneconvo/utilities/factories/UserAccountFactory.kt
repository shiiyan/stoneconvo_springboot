package com.stoneconvo.utilities.factories

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.userAccount.PasswordHash
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountName
import com.stoneconvo.utilities.generators.RandomStringGenerator
import com.stoneconvo.utilities.generators.UserAccountIdGenerator

object UserAccountFactory {
    fun makeUserAccountName(name: String) = UserAccountName(name)
    fun makePasswordHash(plainText: String) = PasswordHash(Helper.generateHash(plainText))

    fun createRandomly(
        id: UserAccountId = UserAccountIdGenerator.perform(),
        name: UserAccountName = makeUserAccountName(RandomStringGenerator.perform()),
        passwordHash: PasswordHash = makePasswordHash(RandomStringGenerator.perform(length = 12)),
        creator: Administrator = AdministratorFactory.createRandomly()
    ) = UserAccount(
        id = id,
        name = name,
        passwordHash = passwordHash,
        creator = creator
    )
}
