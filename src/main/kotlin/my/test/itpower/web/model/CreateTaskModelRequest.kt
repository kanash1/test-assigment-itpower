package my.test.itpower.web.model

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import my.test.itpower.application.dto.CreateTaskDtoRequest
import java.util.Date

data class CreateTaskModelRequest(
    @field:NotBlank
    val name: String,
    @field:NotNull
    val date: Date,
    @field:NotBlank
    val description: String
)

fun CreateTaskModelRequest.toDto(userId: Long) = CreateTaskDtoRequest(userId, name, date, description)
