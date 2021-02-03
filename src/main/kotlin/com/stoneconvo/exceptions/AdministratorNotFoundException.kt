package com.stoneconvo.exceptions

import com.stoneconvo.domain.userAccount.UserAccountId

class AdministratorNotFoundException(userId: UserAccountId) :
    Exception("Administrator Not Found - UserId: $userId")
