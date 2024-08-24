package my.test.itpower.web.controller

import jakarta.validation.Valid
import my.test.itpower.application.service.AuthService
import my.test.itpower.web.model.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthService,
    @Value("\${security.jwt.access.expiration}")
    private val jwtAccessExpiration: Long,
) {
    @PostMapping("/access-tokens")
    fun createAccessToken(
        @RequestBody @Valid createAccessTokenModelRequest: CreateAccessTokenModelRequest
    ): AccessTokenModelResponse = authService.createAccessToken(createAccessTokenModelRequest.toDto()).toModel()
}