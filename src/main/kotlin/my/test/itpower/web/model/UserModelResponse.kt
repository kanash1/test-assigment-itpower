package my.test.itpower.web.model

import my.test.itpower.application.dto.UserDtoResponse

data class UserModelResponse(
    val id: Long,
    val email: String,
    val username: String
)

fun UserDtoResponse.toModel() = UserModelResponse(id, email, username)
