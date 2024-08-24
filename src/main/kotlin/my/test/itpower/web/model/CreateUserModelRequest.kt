package my.test.itpower.web.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import my.test.itpower.application.dto.CreateUserDtoRequest

data class CreateUserModelRequest(
    @field:Email
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)

fun CreateUserModelRequest.toDto() = CreateUserDtoRequest(email, username, password)