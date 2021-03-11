package com.stoneconvo.filter

import org.springframework.core.annotation.Order
import org.springframework.web.filter.AbstractRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

@Order(1)
class RequestLoggingFilter : AbstractRequestLoggingFilter() {
    init {
        this.setIncludeClientInfo(true)
        this.setIncludeHeaders(true)
        this.setIncludePayload(true)
        this.setIncludeQueryString(true)
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }
}
