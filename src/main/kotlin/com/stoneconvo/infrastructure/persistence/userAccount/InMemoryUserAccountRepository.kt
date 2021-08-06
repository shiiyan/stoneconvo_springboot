package com.stoneconvo.infrastructure.persistence.userAccount

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.persistence.InMemoryBaseRepository
import com.stoneconvo.domain.userAccount.UserAccount
import com.stoneconvo.domain.userAccount.UserAccountName
import com.stoneconvo.domain.userAccount.UserAccountRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("test")
class InMemoryUserAccountRepository : UserAccountRepository {
    private val repository: InMemoryBaseRepository<UserAccount.Dto> = InMemoryBaseRepository()

    fun reset() {
        repository.reset()
    }

    fun count() = repository.count()

    override fun findByUserId(userId: UserAccountId): UserAccount.Dto? =
        repository.findBy { it.id == userId.value }

    override fun findByUserName(userName: UserAccountName): UserAccount.Dto? =
        repository.findBy { it.name == userName.value }

    override fun insert(userAccount: UserAccount) {
        repository.insert(userAccount.toDto())
    }

    override fun update(userAccount: UserAccount) {
        repository.update(userAccount.toDto())
    }
}
