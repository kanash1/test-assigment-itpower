package my.test.itpower.application.dto

data class DelegateTaskDtoRequest(
    val taskId: Long,
    val userId: Long,
    val receiverEmail: String
)