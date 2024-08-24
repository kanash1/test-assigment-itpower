package my.test.itpower.web.model

import my.test.itpower.application.dto.PartialUpdateTaskDtoRequest
import java.util.Date

data class PartialUpdateTaskModelRequest(
    val name: String?,
    val date: Date?,
    val description: String?
)

fun PartialUpdateTaskModelRequest.toDto(taskId: Long, userId: Long) =
    PartialUpdateTaskDtoRequest(taskId, userId, name, date, description)