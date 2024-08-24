package my.test.itpower.application.dto

import my.test.itpower.persistence.entity.UserEntity

data class CreateUserDtoRequest(
    val email: String,
    val username: String,
    val password: String
)

fun CreateUserDtoRequest.toEntity(encoder: (String) -> String) = UserEntity(email, encoder(password), username)