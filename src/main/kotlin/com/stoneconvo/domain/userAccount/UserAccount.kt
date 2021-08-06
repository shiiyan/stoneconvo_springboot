package com.stoneconvo.domain.userAccount

import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.domain.administrator.Administrator

class UserAccount(
    override val id: UserAccountId,
    var name: UserAccountName,
    var passwordHash: PasswordHash,
    val creator: Administrator
) : Entity<UserAccount.Dto>(id) {
    companion object {
        fun create(
            name: UserAccountName,
            passwordHash: PasswordHash,
            creator: Administrator,
        ) = UserAccount(
            id = UserAccountId.create(),
            name = name,
            passwordHash = passwordHash,
            creator = creator
        )

        fun fromDto(dto: Dto) = UserAccount(
            id = UserAccountId(dto.id),
            name = UserAccountName(dto.name),
            passwordHash = PasswordHash(dto.passwordHash),
            creator = Administrator(UserAccountId(dto.creatorId))
        )
    }

    fun changeName(newName: UserAccountName) {
        name = newName
    }

    fun changePassword(newPasswordHash: PasswordHash) {
        passwordHash = newPasswordHash
    }

    override fun toDto(): Dto = Dto(
        id = id.value,
        name = name.value,
        passwordHash = passwordHash.value,
        creatorId = creator.id.value
    )

    data class Dto(
        override val id: String,
        val name: String,
        val passwordHash: String,
        val creatorId: String
    ) : Entity.Dto(id)
}
