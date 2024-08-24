package my.test.itpower.application.dto

import my.test.itpower.persistence.entity.UserEntity

data class UserDtoResponse(
    val id: Long,
    val email: String,
    val username: String
)

fun UserEntity.toDto() = UserDtoResponse(id!!, email, username)
