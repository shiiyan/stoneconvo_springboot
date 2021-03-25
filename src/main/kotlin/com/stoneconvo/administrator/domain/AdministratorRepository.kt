package com.stoneconvo.administrator.domain

import com.stoneconvo.common.domain.UserAccountId

interface AdministratorRepository {
    fun findByUserId(userId: UserAccountId): Administrator?
}
