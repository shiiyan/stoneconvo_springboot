package com.stoneconvo.userInterface.controller.requestBody

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateAccountRequestBody(
    @field:[NotBlank Size(max = 30)]
    val name: String,
    @field:NotBlank
    val password: String
)
