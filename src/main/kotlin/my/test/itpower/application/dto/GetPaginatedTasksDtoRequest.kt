package my.test.itpower.application.dto

data class GetPaginatedTasksDtoRequest(
    val userId: Long,
    val offset: Int,
    val limit: Int
)
