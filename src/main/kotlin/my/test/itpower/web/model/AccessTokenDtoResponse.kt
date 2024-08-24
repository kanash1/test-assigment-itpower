package my.test.itpower.web.model

import my.test.itpower.application.dto.AccessTokenDtoResponse

data class AccessTokenModelResponse(
    val userId: Long,
    val email: String,
    val username: String,
    val accessToken: String
)

fun AccessTokenDtoResponse.toModel() = AccessTokenModelResponse(userId, email, username, accessToken)
