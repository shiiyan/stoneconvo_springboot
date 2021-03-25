package com.stoneconvo.administrator.domain

import com.stoneconvo.userAccount.domain.UserAccountId

interface AdministratorRepository {
    fun findByUserId(userId: UserAccountId): Administrator?
}
