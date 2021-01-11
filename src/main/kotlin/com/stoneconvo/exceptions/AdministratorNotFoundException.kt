package com.stoneconvo.exceptions

import com.stoneconvo.domain.userAccount.UserAccountId

class AdministratorNotFoundException(userId: UserAccountId, message: String) :
    Exception("$message - UserId: $userId")
