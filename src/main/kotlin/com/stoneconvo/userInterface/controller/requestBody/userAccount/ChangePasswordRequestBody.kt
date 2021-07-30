package com.stoneconvo.userInterface.controller.requestBody.userAccount

import javax.validation.constraints.NotBlank

data class ChangePasswordRequestBody(
    @field:NotBlank
    val password: String
)
