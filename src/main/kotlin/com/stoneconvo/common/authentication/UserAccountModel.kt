package com.stoneconvo.common.authentication

import org.springframework.data.relational.core.mapping.Column

class UserAccountModel(
    @Column("user_account_id")
    val userAccountId: String,
    @Column("account_name")
    private val userAccountName: String,
    @Column("password_hash")
    private val passwordHash: String
) {
    fun verify(passwordHash: String) = this.passwordHash === passwordHash
}
