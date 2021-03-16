package com.stoneconvo.exceptions

import com.stoneconvo.domain.userAccount.UserAccountId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class AdministratorNotFoundException(userId: UserAccountId) :
    Exception("Administrator Not Found - UserId: $userId")
