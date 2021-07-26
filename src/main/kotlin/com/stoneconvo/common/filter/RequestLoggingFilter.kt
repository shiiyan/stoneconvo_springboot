package com.stoneconvo.common.filter

import org.springframework.core.annotation.Order
import org.springframework.web.filter.AbstractRequestLoggingFilter
import javax.servlet.http.HttpServletRequest

@Order(1)
class RequestLoggingFilter : AbstractRequestLoggingFilter() {
    init {
        super.setIncludeClientInfo(true)
        super.setIncludeHeaders(true)
        super.setIncludePayload(true)
        super.setIncludeQueryString(true)
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        logger.info(message)
    }
}
