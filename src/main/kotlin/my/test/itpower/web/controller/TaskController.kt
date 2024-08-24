package my.test.itpower.web.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import my.test.itpower.application.dto.GetPaginatedTasksDtoRequest
import my.test.itpower.application.dto.UserDtoResponse
import my.test.itpower.application.extractTokenFromHeader
import my.test.itpower.application.getClaimsFromTokenWithVerify
import my.test.itpower.application.service.TaskService
import my.test.itpower.web.model.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/tasks")
class TaskController(
    private val taskService: TaskService,
    private val objectMapper: ObjectMapper,
    @Value("\${security.jwt.access.secret}")
    private val jwtAccessSecret: String,
) {
    @PostMapping
    fun createTask(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestBody @Valid createTaskModelRequest: CreateTaskModelRequest
    ): TaskModelResponse {
        val userDto = extractAccessTokenSubject(authHeader)
        return taskService.createTask(createTaskModelRequest.toDto(userDto.id)).toModel()
    }

    @PatchMapping("/{taskId}")
    fun partialUpdateTask(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @PathVariable("taskId") taskId: Long,
        @RequestBody @Valid partialUpdateTaskModelRequest: PartialUpdateTaskModelRequest
    ): TaskModelResponse {
        val userDto = extractAccessTokenSubject(authHeader)
        return taskService.partialUpdateTask(partialUpdateTaskModelRequest.toDto(taskId, userDto.id)).toModel()
    }

    @DeleteMapping("/{taskId}")
    fun deleteTask(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @PathVariable("taskId") taskId: Long
    ) {
        val userDto = extractAccessTokenSubject(authHeader)
        taskService.deleteTask(userDto.id, taskId)
    }

    @GetMapping(params = ["offset", "limit"])
    fun getTasks(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @RequestParam(value = "offset", defaultValue = "0") @Min(0) offset: Int,
        @RequestParam(value = "limit", defaultValue = "10") @Min(1) limit: Int
    ): List<TaskModelResponse> {
        val userDto = extractAccessTokenSubject(authHeader)
        return taskService.getPaginatedTasks(GetPaginatedTasksDtoRequest(userDto.id, offset, limit)).map { it.toModel() }
    }

    @PostMapping("/{taskId}")
    fun delegateTask(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
        @PathVariable("taskId") taskId: Long,
        @RequestBody @Valid delegateTaskModelRequest: DelegateTaskModelRequest,
    ) {
        val userDto = extractAccessTokenSubject(authHeader)
        taskService.delegateTask(delegateTaskModelRequest.toDto(taskId, userDto.id))
    }

    private fun extractAccessTokenSubject(authHeader: String): UserDtoResponse {
        val token = extractTokenFromHeader(authHeader)
        val userDtoString = getClaimsFromTokenWithVerify(token, jwtAccessSecret).subject
        return objectMapper.readValue<UserDtoResponse>(userDtoString)
    }
}