package my.test.itpower.web.model

import my.test.itpower.application.dto.TaskDtoResponse
import java.util.Date

data class TaskModelResponse(
    val id: Long,
    val userId: Long,
    val name: String,
    val date: Date,
    val description: String
)

fun TaskDtoResponse.toModel() = TaskModelResponse(id, userId, name, date, description)


