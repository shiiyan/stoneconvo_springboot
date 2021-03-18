package com.stoneconvo.exceptions

class UserAccountNotFoundException(
    userId: String? = null,
    userName: String? = null
) :
    Exception(
        "User Account Not Found - ${
        if (userId != null) "userId: $userId" else ""
        } ${
        if (userName != null) "userName: $userName" else ""
        } "
    )
