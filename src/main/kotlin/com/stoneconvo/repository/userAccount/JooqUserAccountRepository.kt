package com.stoneconvo.repository.userAccount

import com.stoneconvo.codegen.tables.daos.JUserAccountsDao
import com.stoneconvo.codegen.tables.pojos.JUserAccounts
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.userAccount.PasswordHash
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountId
import com.stoneconvo.domain.userAccount.UserAccountName
import org.springframework.stereotype.Repository

@Repository
class JooqUserAccountRepository : UserAccountRepository {
    private val dao = JUserAccountsDao()

    override fun findByUserId(userId: UserAccountId): UserAccount? =
        dao.fetchOneByJUserAccountId(userId.value)?.let {
            UserAccount(
                id = UserAccountId(it.userAccountId),
                name = UserAccountName(it.accountName),
                passwordHash = PasswordHash(it.passwordHash),
                creator = Administrator(UserAccountId(it.creatorId))
            )
        }

    override fun insert(userAccount: UserAccount) {
        val userAccountPojo = JUserAccounts()
        userAccountPojo.userAccountId = userAccount.id.value
        userAccountPojo.accountName = userAccount.name.value
        userAccountPojo.passwordHash = userAccount.passwordHash.value
        userAccountPojo.creatorId = userAccount.creator.id.value

        dao.insert(userAccountPojo)
    }

    override fun update(userAccount: UserAccount) {
        val userAccountPojo = JUserAccounts()
        userAccountPojo.userAccountId = userAccount.id.value
        userAccountPojo.accountName = userAccount.name.value
        userAccountPojo.passwordHash = userAccount.passwordHash.value
        userAccountPojo.creatorId = userAccount.creator.id.value

        dao.update(userAccountPojo)
    }
}
