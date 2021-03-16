package com.stoneconvo.controller.userAccount

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user_account")
class UserAccountController {
    @PostMapping("/create")
    fun create() {}

    @PostMapping("/login")
    fun login() {}

    @GetMapping("/logout")
    fun logout() {}
}
