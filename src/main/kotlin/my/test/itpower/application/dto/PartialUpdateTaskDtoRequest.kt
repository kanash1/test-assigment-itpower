package my.test.itpower.application.dto

import java.util.Date

data class PartialUpdateTaskDtoRequest(
    val taskId: Long,
    val userId: Long,
    val name: String?,
    val date: Date?,
    val description: String?
)