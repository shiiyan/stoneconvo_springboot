package com.stoneconvo.common.authentication

import com.stoneconvo.common.helper.Helper
import org.springframework.data.relational.core.mapping.Column

class AuthUser(
    @Column("user_account_id")
    val userAccountId: String,
    @Column("account_name")
    private val userAccountName: String,
    @Column("password_hash")
    private val passwordHash: String
) {
    fun verify(
        userAccountName: String,
        password: String
    ) = this.userAccountName == userAccountName && this.passwordHash == Helper.generateHash(password)
}
