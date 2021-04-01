package com.stoneconvo.administrator.infrastructure.persistence

import com.stoneconvo.administrator.domain.Administrator
import com.stoneconvo.administrator.domain.AdministratorRepository
import com.stoneconvo.codegen.tables.daos.JAdministrationAuthoritiesDao
import com.stoneconvo.common.domain.UserAccountId
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class JooqAdministratorRepository(
    @Autowired
    private val dslContext: DSLContext
) : AdministratorRepository {
    private val dao = JAdministrationAuthoritiesDao(dslContext.configuration())

    override fun findByUserId(userId: UserAccountId): Administrator? =
        dao.fetchOneByJUserAccountId(userId.value)?.let {
            Administrator(
                UserAccountId(it.userAccountId)
            )
        }
}
