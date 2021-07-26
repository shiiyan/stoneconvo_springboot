package com.stoneconvo.userInterface.controller

import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/test")
class TestController {
    @GetMapping("/show_cookie")
    fun test(@CookieValue("test-cookie") testCookie: String) =
        "Hello from SpringBoot with testCookie $testCookie"

    @GetMapping("/new_cookie")
    fun createNewCookie(response: HttpServletResponse): String {
        val newCookie = Cookie("test-cookie", "I'm_new_test_cookie")

        response.addCookie(newCookie)

        return "new cookie is set"
    }

    @GetMapping("/current_user")
    fun showCurrentUser(@CookieValue("user-id") userId: String) =
        "Current user id is $userId"
}
