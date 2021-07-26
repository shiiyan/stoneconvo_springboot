package com.stoneconvo.common.authentication

import com.stoneconvo.codegen.tables.JUserAccounts
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class AuthUserRepository(
    @Autowired
    private val dslContext: DSLContext
) {
    companion object {
        private val USER_ACCOUNT = JUserAccounts()
    }

    fun findUserAccountByName(name: String): AuthUser? =
        dslContext
            .select()
            .from(USER_ACCOUNT)
            .where(USER_ACCOUNT.ACCOUNT_NAME.eq(name))
            .fetchOne()
            ?.into(AuthUser::class.java)
}
