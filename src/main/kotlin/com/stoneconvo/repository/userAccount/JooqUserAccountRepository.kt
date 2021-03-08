package com.stoneconvo.repository.userAccount

import com.stoneconvo.codegen.tables.daos.JUserAccountsDao
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
    }

    override fun update(userAccount: UserAccount) {
        TODO("Not yet implemented")
    }
}
