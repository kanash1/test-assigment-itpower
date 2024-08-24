package my.test.itpower.web.controller

import jakarta.validation.Valid
import my.test.itpower.application.service.TaskService
import my.test.itpower.application.service.UserService
import my.test.itpower.web.model.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
    private val taskService: TaskService
) {
    @PostMapping
    fun createUser(@RequestBody @Valid createUserModelRequest: CreateUserModelRequest): UserModelResponse =
        userService.createUser(createUserModelRequest.toDto()).toModel()

    @GetMapping
    fun getUsers(): List<UserModelResponse> = userService.getAllUsers().map { it.toModel() }
}