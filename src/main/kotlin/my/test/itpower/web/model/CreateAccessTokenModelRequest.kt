package my.test.itpower.web.model

import jakarta.validation.constraints.NotBlank
import my.test.itpower.application.dto.CreateAccessTokenDtoRequest

data class CreateAccessTokenModelRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String,
)

fun CreateAccessTokenModelRequest.toDto() = CreateAccessTokenDtoRequest(email, password)
