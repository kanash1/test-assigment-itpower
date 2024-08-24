package my.test.itpower.persistence.repository

import my.test.itpower.persistence.entity.TaskEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<TaskEntity, Long> {
    fun findByUserId(userId: Long, pageable: Pageable): Page<TaskEntity>
    fun findByIdAndUserId(taskId: Long, userId: Long): TaskEntity
    fun deleteByIdAndUserId(taskId: Long, userId: Long): Int
}