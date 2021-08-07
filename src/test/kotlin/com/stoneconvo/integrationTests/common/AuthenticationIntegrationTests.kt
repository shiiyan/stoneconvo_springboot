package com.stoneconvo.integrationTests.common

import org.assertj.core.api.Assertions.assertThat
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationIntegrationTests {
    @LocalServerPort
    private var port: Int = 8080
    private val restTemplate: TestRestTemplate = TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES)

    @Test
    fun `given normal condition when login and logout then succeed`() {
        val loginHeaders = HttpHeaders()
        loginHeaders.contentType = MediaType.APPLICATION_JSON
        val loginUserAccount = JSONObject(
            mapOf("name" to "admin", "password" to "admin")
        )
        val loginRequest = HttpEntity<String>(loginUserAccount.toString(), loginHeaders)

        val loginResponse = restTemplate
            .postForEntity("http://localhost:$port/login", loginRequest, String::class.java)

        assertThat(loginResponse).isNotNull
        assertThat(loginResponse.statusCode).isEqualTo(HttpStatus.OK)

        val logoutHeaders = HttpHeaders()
        logoutHeaders
            .add(
                "Cookie",
                loginResponse.headers.getFirst("Set-Cookie")
            )
        val logoutRequest = HttpEntity<String>(null, logoutHeaders)

        val logoutResponse = restTemplate
            .postForEntity("http://localhost:$port/logout", logoutRequest, String::class.java)

        assertThat(logoutResponse).isNotNull
        assertThat(logoutResponse.statusCode).isEqualTo(HttpStatus.OK)
    }
}
