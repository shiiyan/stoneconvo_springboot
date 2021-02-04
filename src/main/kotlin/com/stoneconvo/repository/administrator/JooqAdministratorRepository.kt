package com.stoneconvo.repository.administrator

import com.stoneconvo.codegen.tables.daos.JAdministratorsDao
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.userAccount.UserAccountId
import org.springframework.stereotype.Repository

@Repository
class JooqAdministratorRepository : AdministratorRepository {
    private val dao = JAdministratorsDao()

    override fun findByUserId(userId: UserAccountId): Administrator? =
        dao.fetchOneByJUserAccountId(userId.value)?.let {
            Administrator(
                UserAccountId(it.userAccountId)
            )
        }
}
