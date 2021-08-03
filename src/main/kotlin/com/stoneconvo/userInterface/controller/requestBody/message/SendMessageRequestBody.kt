package com.stoneconvo.userInterface.controller.requestBody.message

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SendMessageRequestBody(
    @field: NotBlank
    val roomId: String,
    @field: [NotBlank Size(max = 1000)]
    val messageContent: String
)
