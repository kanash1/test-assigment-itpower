package my.test.itpower.web.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import my.test.itpower.application.dto.UserDtoResponse
import my.test.itpower.application.extractTokenFromHeader
import my.test.itpower.application.getClaimsFromTokenWithVerify
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class AuthTokenFilter(
    @Value("\${security.jwt.access.secret}")
    private val jwtAccessSecret: String,
    private val userDetailService: UserDetailsService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private val log = KotlinLogging.logger {  }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = extractTokenFromHeader(request)
            val userDtoString: String = getClaimsFromTokenWithVerify(token, jwtAccessSecret).subject
            val userDto = objectMapper.readValue<UserDtoResponse>(userDtoString)
            val user: UserDetails = userDetailService.loadUserByUsername(userDto.email)
            val authToken = UsernamePasswordAuthenticationToken(
                user,
                null,
                user.authorities
            )
            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken
        } catch (e: Exception) {
            log.debug { e.message }
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(request, response)
    }

}