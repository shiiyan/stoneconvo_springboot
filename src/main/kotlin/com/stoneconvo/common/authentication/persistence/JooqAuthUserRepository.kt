package com.stoneconvo.common.authentication.persistence

import com.stoneconvo.codegen.tables.JUserAccounts
import com.stoneconvo.common.authentication.AuthUser
import com.stoneconvo.common.authentication.AuthUserRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("default")
class JooqAuthUserRepository(
    @Autowired
    private val dslContext: DSLContext
) : AuthUserRepository {
    companion object {
        private val USER_ACCOUNT = JUserAccounts()
    }

    override fun findUserAccountByName(name: String): AuthUser? =
        dslContext
            .select()
            .from(USER_ACCOUNT)
            .where(USER_ACCOUNT.ACCOUNT_NAME.eq(name))
            .fetchOne()
            ?.into(AuthUser::class.java)

    override fun findUserAccountById(id: String): AuthUser? =
        dslContext
            .select()
            .from(USER_ACCOUNT)
            .where(USER_ACCOUNT.USER_ACCOUNT_ID.eq(id))
            .fetchOne()
            ?.into(AuthUser::class.java)
}
