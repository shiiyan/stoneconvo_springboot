package com.stoneconvo.infrastructure.persistence.administrator

import com.stoneconvo.codegen.tables.daos.JAdministrationAuthoritiesDao
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.administrator.AdministratorRepository
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("default")
class JooqAdministratorRepository(
    @Autowired
    private val dslContext: DSLContext
) : AdministratorRepository {
    private val dao = JAdministrationAuthoritiesDao(dslContext.configuration())

    override fun findByUserId(userId: UserAccountId): Administrator.Dto? =
        dao.fetchOneByJUserAccountId(userId.value)?.let {
            Administrator(
                UserAccountId(it.userAccountId)
            ).toDto()
        }
}
