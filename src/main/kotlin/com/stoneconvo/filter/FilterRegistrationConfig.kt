package com.stoneconvo.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterRegistrationConfig {
    @Bean
    fun logFilter(): FilterRegistrationBean<LogFilter> {
        val registrationBean = FilterRegistrationBean<LogFilter>()
        registrationBean.filter = LogFilter()
        registrationBean.addUrlPatterns("*")
        return registrationBean
    }
}
