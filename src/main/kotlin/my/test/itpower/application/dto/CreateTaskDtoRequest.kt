package my.test.itpower.application.dto

import my.test.itpower.persistence.entity.TaskEntity
import my.test.itpower.persistence.entity.UserEntity
import java.util.Date

data class CreateTaskDtoRequest(
    val userId: Long,
    val name: String,
    val date: Date,
    val description: String
)

fun CreateTaskDtoRequest.toEntity(userEntity: UserEntity) = TaskEntity(name, date, userEntity, description)
