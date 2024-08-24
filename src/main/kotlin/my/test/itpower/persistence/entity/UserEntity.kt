package my.test.itpower.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "\"user\"")
class UserEntity(
    @Column(name = "user_email", nullable = false, unique = true)
    var email: String,
    @Column(name = "user_password_hash", nullable = false)
    var passwordHash: String,
    @Column(name = "user_name", nullable = false)
    var username: String,
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var tasks: Set<TaskEntity> = emptySet(),
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    val id: Long? = null
)