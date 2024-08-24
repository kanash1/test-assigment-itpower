package my.test.itpower.web.model

import jakarta.validation.constraints.NotBlank
import my.test.itpower.application.dto.DelegateTaskDtoRequest

data class DelegateTaskModelRequest(
    @field:NotBlank
    val receiverEmail: String
)

fun DelegateTaskModelRequest.toDto(taskId: Long, userId: Long) = DelegateTaskDtoRequest(taskId, userId, receiverEmail)