package com.stoneconvo.repository.administrator

import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.domain.userAccount.UserAccountId

interface AdministratorRepository {
    fun findByUserId(userId: UserAccountId): Administrator?
}
