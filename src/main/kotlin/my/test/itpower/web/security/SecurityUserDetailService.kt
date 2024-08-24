package my.test.itpower.web.security

import my.test.itpower.persistence.entity.UserEntity
import my.test.itpower.persistence.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class SecurityUserDetailService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails = userRepository.findByEmail(email).toUser()

    private fun UserEntity.toUser() = User(email, passwordHash, emptySet())
}