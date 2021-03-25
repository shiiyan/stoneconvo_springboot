package com.stoneconvo.common.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterRegistrationConfig {
    @Bean
    fun logFilter(): FilterRegistrationBean<RequestLoggingFilter> = FilterRegistrationBean(RequestLoggingFilter())

    @Bean
    fun authFilter(): FilterRegistrationBean<RequestAuthFilter> = FilterRegistrationBean(RequestAuthFilter())
}
