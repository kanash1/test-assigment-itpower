package my.test.itpower.application.service

import my.test.itpower.application.dto.CreateUserDtoRequest
import my.test.itpower.application.dto.UserDtoResponse
import my.test.itpower.application.dto.toDto
import my.test.itpower.application.dto.toEntity
import my.test.itpower.application.exception.EmailAlreadyExistException
import my.test.itpower.persistence.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
) {
    fun getAllUsers(): List<UserDtoResponse> = userRepository.findAll().map { it.toDto() }

    fun createUser(createUserDtoRequest: CreateUserDtoRequest): UserDtoResponse {
        if (userRepository.existsByEmail(createUserDtoRequest.email))
            throw EmailAlreadyExistException("User email exists")
        var userEntity = createUserDtoRequest.toEntity { encoder.encode(it) }
        userEntity = userRepository.save(userEntity)
        return userEntity.toDto()
    }
}