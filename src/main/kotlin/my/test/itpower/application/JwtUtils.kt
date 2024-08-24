package my.test.itpower.application

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import java.util.*
import javax.crypto.SecretKey


fun generateToken(secret: String, expiration: Long, claims: Map<String, Any>): String {
    val issueDate = Date()
    val expiredDate = Date(issueDate.time + expiration)
    return Jwts.builder()
        .claims(claims)
        .issuedAt(issueDate)
        .expiration(expiredDate)
        .signWith(key(secret), Jwts.SIG.HS256)
        .id(UUID.randomUUID().toString())
        .compact()
}

@Throws(JwtException::class)
fun getClaimsFromTokenWithVerify(token: String, secret: String): Claims =
    Jwts.parser().verifyWith(key(secret)).build().parseSignedClaims(token).payload

fun extractTokenFromHeader(request: HttpServletRequest): String {
    val headerAuth: String? = request.getHeader("Authorization")
    if (headerAuth != null)
        return extractTokenFromHeader(headerAuth)
    else
        throw IllegalArgumentException("No jwt token in header")
}

fun extractTokenFromHeader(headerAuth: String): String {
    if (headerAuth.startsWith("Bearer ")) {
        return headerAuth.substring(7)
    }
    throw IllegalArgumentException("No jwt token in header")
}

private fun key(secret: String): SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())