package my.test.itpower.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import my.test.itpower.application.dto.*
import my.test.itpower.application.exception.TaskNotFound
import my.test.itpower.application.exception.UserNotFound
import my.test.itpower.persistence.repository.TaskRepository
import my.test.itpower.persistence.repository.UserRepository
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
    private val userRepository: UserRepository,
    private val taskRepository: TaskRepository
) {
    private val log = KotlinLogging.logger {  }

    fun createTask(createTaskDtoRequest: CreateTaskDtoRequest): TaskDtoResponse {
        try {
            val userEntity = userRepository.findById(createTaskDtoRequest.userId).get()
            var taskEntity = createTaskDtoRequest.toEntity(userEntity)
            taskEntity = taskRepository.save(taskEntity)
            return taskEntity.toDto()
        } catch (e: NoSuchElementException) {
            log.debug { e.message }
            throw UserNotFound("User with id ${createTaskDtoRequest.userId} not found", e)
        }
    }

    fun partialUpdateTask(partialUpdateTaskDtoRequest: PartialUpdateTaskDtoRequest): TaskDtoResponse {
        try {
            val taskEntity = partialUpdateTaskDtoRequest.run { taskRepository.findByIdAndUserId(taskId, userId) }
            taskEntity.apply {
                name = partialUpdateTaskDtoRequest.name ?: name
                date = partialUpdateTaskDtoRequest.date ?: date
                description = partialUpdateTaskDtoRequest.description ?: description
            }
            return taskRepository.save(taskEntity).toDto()
        } catch (e: EmptyResultDataAccessException) {
            log.debug { e.message }
            throw partialUpdateTaskDtoRequest.run {
                TaskNotFound("Task with id $taskId and owner with id $userId not found", e)
            }
        }

    }

    @Transactional
    fun deleteTask(userId: Long, taskId: Long) {
        if (taskRepository.deleteByIdAndUserId(taskId, userId) == 0)
            throw TaskNotFound("Task with id $taskId and owner with id $userId not found")
    }

    fun getPaginatedTasks(getPaginatedTasksDtoRequest: GetPaginatedTasksDtoRequest): List<TaskDtoResponse> =
        try {
            getPaginatedTasksDtoRequest.run {
                taskRepository.findByUserId(userId, PageRequest.of(offset, limit)).toList().map { it.toDto() }
            }
        } catch (e: EmptyResultDataAccessException) {
            log.debug { e.message }
            throw UserNotFound("User with id ${getPaginatedTasksDtoRequest.userId} not found", e)
        }

    @Transactional
    fun delegateTask(delegateTaskDtoRequest: DelegateTaskDtoRequest) {
        try {
            val receiverUserEntity = userRepository.findByEmail(delegateTaskDtoRequest.receiverEmail)
            if (receiverUserEntity.id != delegateTaskDtoRequest.userId) {
                try {
                    val taskEntity = delegateTaskDtoRequest.run { taskRepository.findByIdAndUserId(taskId, userId) }
                    taskEntity.user = receiverUserEntity
                    taskRepository.save(taskEntity)
                } catch (e: EmptyResultDataAccessException) {
                    log.debug { e.message }
                    throw delegateTaskDtoRequest.run {
                        TaskNotFound("Task with id $taskId and owner with id $userId not found", e)
                    }
                }
            }
        } catch (e: EmptyResultDataAccessException) {
            log.debug { e.message }
            throw UserNotFound("User with email ${delegateTaskDtoRequest.receiverEmail} not found", e)
        }
    }
}