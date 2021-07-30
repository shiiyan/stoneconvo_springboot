package com.stoneconvo.userInterface.controller.requestBody

import javax.validation.constraints.NotBlank

data class ChangePasswordRequestBody(
    @field:NotBlank
    val password: String
)
