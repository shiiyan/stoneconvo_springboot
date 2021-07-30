package com.stoneconvo.userInterface.controller.requestBody.chatRoom

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AddMemberRequestBody(
    @field:NotBlank
    val chatRoomId: String,
    @field:NotBlank
    val userAccountId: String,
    @field:[NotBlank Size(max = 30)]
    val memberName: String
)
