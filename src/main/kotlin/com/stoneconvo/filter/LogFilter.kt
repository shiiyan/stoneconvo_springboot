package com.stoneconvo.filter

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(1)
class LogFilter : OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(LogFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info(
            "Request URL path: ${request.requestURI}, " +
                "Request cookies: ${request.cookies?.first()?.name}=${request.cookies?.first()?.value}"
        )
        filterChain.doFilter(request, response)
    }
}
