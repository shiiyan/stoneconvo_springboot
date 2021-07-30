package com.stoneconvo.userInterface.controller.requestBody.chatRoom

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class ChangeRoomNameRequestBody(
    @field:[NotBlank Pattern(regexp = "^[A-Za-z0-9]{20}$")]
    val roomId: String,
    @field:[NotBlank Size(max = 30)]
    val name: String
)
