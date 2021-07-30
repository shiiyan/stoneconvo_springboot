package com.stoneconvo.userInterface.controller.requestBody

import javax.validation.constraints.NotBlank

data class RemoveMemberRequestBody(
    @field:NotBlank
    val chatRoomId: String,
    @field:NotBlank
    val userAccountId: String
)
