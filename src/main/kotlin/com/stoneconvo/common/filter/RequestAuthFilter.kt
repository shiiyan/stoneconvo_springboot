package com.stoneconvo.common.filter

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(1)
class RequestAuthFilter : OncePerRequestFilter() {
    companion object {
        private const val PATTERN = "^[A-Za-z0-9]{20}$"
    }

    object UnauthorizedErrorResponseBody {
        const val message = "Request Unauthorized"
        val status = HttpStatus.FORBIDDEN.value()
        val timestamp = LocalDateTime.now()
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // TODO : refine verify user-id logic
        if (!hasUserIdInCookies(request)) {
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.status = HttpStatus.FORBIDDEN.value()
            val printWriter = response.writer
            printWriter.print(
                """
                {
                    "message": "${UnauthorizedErrorResponseBody.message}", 
                    "status": ${UnauthorizedErrorResponseBody.status},
                    "timestamp": "${UnauthorizedErrorResponseBody.timestamp}"
                }
                """.trimIndent()
            )
            printWriter.flush()
            printWriter.close()
            return
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        "^(/login.*)$".toRegex().matches(request.requestURI)

    private fun hasUserIdInCookies(request: HttpServletRequest) =
        request.cookies
            ?.any { cookie -> cookie.name == "user-id" && PATTERN.toRegex().matches(cookie.value) }
            ?: false
}
