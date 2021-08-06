package com.stoneconvo.utilities.generators

import com.stoneconvo.common.domain.UserAccountId
import com.stoneconvo.common.helper.Helper

object UserAccountIdGenerator {
    fun perform(): UserAccountId = UserAccountId(value = Helper.generateRandomId())
}
