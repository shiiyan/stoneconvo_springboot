package com.stoneconvo.common.authorization

interface AuthorizationService {
    fun authorize()
    fun getCurrentUserId(): String
}
