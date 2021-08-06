package com.stoneconvo.domain.administrator

import com.stoneconvo.common.domain.UserAccountId

interface AdministratorRepository {
    fun findByUserId(userId: UserAccountId): Administrator.Dto?
}
