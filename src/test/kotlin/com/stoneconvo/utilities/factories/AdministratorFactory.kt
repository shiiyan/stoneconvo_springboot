package com.stoneconvo.utilities.factories

import com.stoneconvo.domain.administrator.Administrator
import com.stoneconvo.utilities.generators.UserAccountIdGenerator

object AdministratorFactory {
    fun createRandomly(): Administrator = Administrator(
        id = UserAccountIdGenerator.perform()
    )
}
