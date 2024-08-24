package my.test.itpower.application.dto

import my.test.itpower.persistence.entity.TaskEntity
import java.util.Date

data class TaskDtoResponse(
    val id: Long,
    val userId: Long,
    val name: String,
    val date: Date,
    val description: String
)

fun TaskEntity.toDto() = TaskDtoResponse(id!!, user.id!!, name, date, description)


