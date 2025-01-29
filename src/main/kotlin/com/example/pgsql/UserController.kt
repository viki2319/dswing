package com.example.pgsql

import org.springframework.web.bind.annotation.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController(
    private val userService: UserService,
    private val eventPublisher: EventPublisher,
// Inject EventPublisher to send audit messages
    private val auditLogService: AuditLogService // Inject AuditLogService
) {

    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping("/name/{name}")
    fun searchUsers(@PathVariable name: String): List<User> {
        logger.info("Searching for users with name containing: $name")
        val users = userService.searchUsersByName(name)
        publishAuditEvent("searchUsers", "Searching users by name: $name", HttpStatus.OK.value())
        return users
    }

    @GetMapping
    fun getAllUsers(): List<User> {
        logger.info("Fetching all users data")
        val users = userService.getAllUsers()
        publishAuditEvent("getAllUsers", "Fetched all users", HttpStatus.OK.value())
        return users
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): User? {
        logger.info("Fetching user data for ID: $userId")
        val user = userService.getUserById(userId)
        if (user == null) {
            logger.warn("User with ID $userId not found")
            publishAuditEvent("getUserById", "User with ID $userId not found", HttpStatus.NOT_FOUND.value())
        } else {
            publishAuditEvent("getUserById", "Fetched user with ID: $userId", HttpStatus.OK.value())
        }
        return user
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User): User {
        logger.info("Creating user: ${user.name}")
        if (user.name.isNullOrBlank()) {
            logger.warn("User name cannot be empty")
            publishAuditEvent("createUser", "Failed to create user: Name cannot be empty", HttpStatus.BAD_REQUEST.value())
            throw BadRequestException("User name cannot be empty")
        }
        val createdUser = userService.createUser(user)
        publishAuditEvent("createUser", "Created user: ${createdUser.userId}", HttpStatus.CREATED.value())
        return createdUser
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: Long, @RequestBody user: User): User {
        logger.info("Updating user with ID: $userId")
        val existingUser = userService.getUserById(userId)
        if (existingUser == null) {
            logger.error("User with ID $userId not found")
            publishAuditEvent("updateUser", "Failed to update user: User with ID $userId not found", HttpStatus.NOT_FOUND.value())
            throw ResourceNotFoundException("User not found")
        }
        val updatedUser = userService.updateUser(userId, user)
        publishAuditEvent("updateUser", "Updated user with ID: $userId", HttpStatus.OK.value())
        return updatedUser
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userId: Long) {
        logger.info("Deleting user with ID: $userId")
        val user = userService.getUserById(userId)
        if (user == null) {
            logger.warn("User with ID $userId not found")
            publishAuditEvent("deleteUser", "Failed to delete user: User with ID $userId not found", HttpStatus.NOT_FOUND.value())
            throw ResourceNotFoundException("User not found")
        }
        userService.deleteUser(userId)
        publishAuditEvent("deleteUser", "Deleted user with ID: $userId", HttpStatus.NO_CONTENT.value())
        logger.info("User with ID $userId deleted")
    }

    private fun publishAuditEvent(action: String, description: String, statusCode: Int) {
        val auditMessage = AuditMessage(action = action, description = description, statusCode = statusCode)
        eventPublisher.publishPlainMessage(auditMessage.toString())
        val auditLog = AuditLog(
            action = action,
            description = description,
            statusCode = statusCode
        )
        auditLogService.saveAuditLog(auditLog)
        logger.info("Audit log saved to database: $auditLog")
        eventPublisher.publishPlainMessage(auditMessage.toString())
    }

    data class AuditMessage(
        val action: String,
        val description: String,
        val statusCode: Int
    )
}
