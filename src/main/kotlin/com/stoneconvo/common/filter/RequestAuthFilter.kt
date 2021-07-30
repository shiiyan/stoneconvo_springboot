package com.stoneconvo.common.filter

import com.stoneconvo.common.authorization.AuthorizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.lang.Exception
import java.time.LocalDateTime
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(1)
@Component
class RequestAuthFilter(
    @Autowired
    private val authorizationService: AuthorizationService
) : OncePerRequestFilter() {

    object UnauthorizedResponseBody {
        const val message = "Request Unauthorized"
        val status = HttpStatus.FORBIDDEN.value()
        val timestamp: LocalDateTime = LocalDateTime.now()
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            authorizationService.authorize()
        } catch (e: Exception) {
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.status = HttpStatus.FORBIDDEN.value()
            val printWriter = response.writer
            printWriter.print(
                """
                {
                    "message": "${UnauthorizedResponseBody.message}", 
                    "status": ${UnauthorizedResponseBody.status},
                    "timestamp": "${UnauthorizedResponseBody.timestamp}"
                }
                """.trimIndent()
            )
            printWriter.flush()
            printWriter.close()
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        "^(/login.*)$".toRegex().matches(request.requestURI)
}
