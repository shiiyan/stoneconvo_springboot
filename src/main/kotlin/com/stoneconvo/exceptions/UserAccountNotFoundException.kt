package com.stoneconvo.exceptions

import com.stoneconvo.domain.userAccount.UserAccountId

class UserAccountNotFoundException(userAccountId: UserAccountId) :
    Exception("User Account Not Found - UserAccountId: $userAccountId")
