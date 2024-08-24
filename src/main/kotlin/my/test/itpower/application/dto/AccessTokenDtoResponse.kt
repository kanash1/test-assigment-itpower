package my.test.itpower.application.dto

data class AccessTokenDtoResponse(
    val userId: Long,
    val email: String,
    val username: String,
    val accessToken: String
)
