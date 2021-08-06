package com.stoneconvo.common.authentication

interface AuthUserRepository {
    fun findUserAccountByName(name: String): AuthUser?
    fun findUserAccountById(id: String): AuthUser?
}
