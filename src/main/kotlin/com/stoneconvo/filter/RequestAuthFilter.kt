package com.stoneconvo.filter

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(1)
class RequestAuthFilter : OncePerRequestFilter() {
    companion object {
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (!hasUserIdInCookies(request)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Request Unauthorized")
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        "^(/login)$".toRegex().matches(request.requestURI)

    private fun hasUserIdInCookies(request: HttpServletRequest) =
        request.cookies
            ?.any { cookie -> cookie.name == "user-id" && PATTERN.toRegex().matches(cookie.value) }
            ?: false
}
