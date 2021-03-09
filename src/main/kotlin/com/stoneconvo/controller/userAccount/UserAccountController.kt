package com.stoneconvo.controller.userAccount

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user_account")
class UserAccountController {
    @PostMapping("/create")
    fun create() {}
}
