package com.stoneconvo.domain.administrator

import com.stoneconvo.common.domain.Entity
import com.stoneconvo.common.domain.UserAccountId

class Administrator(override val id: UserAccountId) : Entity<Administrator.Dto>(id) {
    companion object {
        fun fromDto(dto: Dto) = Administrator(id = UserAccountId(dto.id))
    }

    data class Dto(
        override val id: String
    ) : Entity.Dto(id)

    override fun toDto(): Dto = Dto(id = id.value)
}
