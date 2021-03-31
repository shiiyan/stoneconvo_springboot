package com.stoneconvo.userAccount.infrastructure.persistence

import com.stoneconvo.administrator.domain.Administrator
import com.stoneconvo.codegen.tables.daos.JUserAccountsDao
import com.stoneconvo.codegen.tables.pojos.JUserAccounts
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.userAccount.domain.PasswordHash
import com.stoneconvo.userAccount.domain.UserAccount
import com.stoneconvo.userAccount.domain.UserAccountName
import com.stoneconvo.userAccount.domain.UserAccountRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class JooqUserAccountRepository(
    @Autowired
    private val dslContext: DSLContext
) : UserAccountRepository {
    private val dao = JUserAccountsDao(dslContext.configuration())

    override fun findByUserId(userId: UserAccountId): UserAccount? =
        dao.fetchOneByJUserAccountId(userId.value)?.let {
            UserAccount(
                id = UserAccountId(it.userAccountId),
                name = UserAccountName(it.accountName),
                passwordHash = PasswordHash(it.passwordHash),
                creator = Administrator(UserAccountId(it.creatorId))
            )
        }

    override fun findByUserName(userName: UserAccountName): UserAccount? =
        dao.fetchOneByJAccountName(userName.value)?.let {
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