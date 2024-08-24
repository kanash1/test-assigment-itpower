package my.test.itpower.persistence.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "task")
class TaskEntity(
    @Column(name = "task_name", nullable = false)
    var name: String,
    @Column(name = "task_date", nullable = false)
    var date: Date,
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    var user: UserEntity,
    @Column(name = "task_description", nullable = false)
    var description: String,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false, unique = true)
    val id: Long? = null
)