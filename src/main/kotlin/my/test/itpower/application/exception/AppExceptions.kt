package my.test.itpower.application.exception

class EmailAlreadyExistException(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class UserNotFound(message: String? = null, cause: Throwable? = null): Exception(message, cause)

class TaskNotFound(message: String? = null, cause: Throwable? = null): Exception(message, cause)