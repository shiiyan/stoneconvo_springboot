package com.stoneconvo.userInterface.controller.requestBody.message

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class EditMessageRequestBody(
    @field: NotBlank
    val messageId: String,
    @field: [NotBlank Size(max = 1000)]
    val messageContent: String
)
