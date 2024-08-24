package my.test.itpower.application.service

import com.fasterxml.jackson.databind.ObjectMapper
import my.test.itpower.application.dto.AccessTokenDtoResponse
import my.test.itpower.application.dto.CreateAccessTokenDtoRequest
import my.test.itpower.application.dto.toDto
import my.test.itpower.application.generateToken
import my.test.itpower.persistence.repository.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(
    @Value("\${security.jwt.access.secret}")
    private val jwtAccessSecret: String,
    @Value("\${security.jwt.access.expiration}")
    private val jwtAccessExpiration: Long,
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val objectMapper: ObjectMapper
) {
    fun createAccessToken(createAccessTokenDtoRequest: CreateAccessTokenDtoRequest): AccessTokenDtoResponse {
        val authentication: Authentication = authenticationManager
            .authenticate(
                UsernamePasswordAuthenticationToken(
                    createAccessTokenDtoRequest.email,
                    createAccessTokenDtoRequest.password
                )
            )
        SecurityContextHolder.getContext().authentication = authentication
        val userEntity = userRepository.findByEmail(createAccessTokenDtoRequest.email)
        val userDtoString = objectMapper.writeValueAsString(userEntity.toDto())
        val accessToken = generateToken(
            jwtAccessSecret,
            jwtAccessExpiration,
            mapOf("sub" to userDtoString)
        )
        return AccessTokenDtoResponse(
            userEntity.id!!,
            userEntity.email,
            userEntity.username,
            accessToken
        )
    }
}