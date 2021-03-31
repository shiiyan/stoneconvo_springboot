package com.stoneconvo.userAccount.infrastructure.http.requestBody

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateRequestBody(
    @field:[NotBlank Size(max = 30)]
    val name: String,
    @field:NotBlank
    val password: String
)
