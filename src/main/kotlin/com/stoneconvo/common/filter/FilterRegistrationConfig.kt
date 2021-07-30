package com.stoneconvo.common.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterRegistrationConfig(
    @Autowired
    private val requestLoggingFilter: RequestLoggingFilter,
    @Autowired
    private val requestAuthFilter: RequestAuthFilter
) {
    @Bean
    fun logFilter(): FilterRegistrationBean<RequestLoggingFilter> = FilterRegistrationBean(requestLoggingFilter)

    @Bean
    fun authFilter(): FilterRegistrationBean<RequestAuthFilter> = FilterRegistrationBean(requestAuthFilter)
}
