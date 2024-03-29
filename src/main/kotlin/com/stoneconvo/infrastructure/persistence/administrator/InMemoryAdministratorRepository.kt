package com.stoneconvo.infrastructure.persistence.administrator

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.persistence.InMemoryBaseRepository
import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.administrator.AdministratorRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("test")
class InMemoryAdministratorRepository : AdministratorRepository {
    private val repository: InMemoryBaseRepository<Administrator.Dto> = InMemoryBaseRepository()

    fun reset() {
        repository.reset()
    }

    fun count() = repository.count()

    override fun findByUserId(userId: UserAccountId): Administrator.Dto? =
        repository.findBy { it.id == userId.value }

    fun insert(administrator: Administrator) {
        repository.insert(administrator.toDto())
    }
}
