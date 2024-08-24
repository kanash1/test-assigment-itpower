package my.test.itpower.web.handler

import my.test.itpower.application.exception.EmailAlreadyExistException
import my.test.itpower.application.exception.TaskNotFound
import my.test.itpower.application.exception.UserNotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistException::class)
    fun handleConflictException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(UserNotFound::class, TaskNotFound::class)
    fun handleNotFound(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }
}